package com.zhaobaoge.widget.tablayout;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.zhaobaoge.widget.R;
import com.zhaobaoge.widget.tablayout.adapter.TabAdapter;
import com.zhaobaoge.widget.tablayout.iter.ITabLayout;
import com.zhaobaoge.widget.tablayout.iter.OnTabSelectedListener;
import com.zhaobaoge.widget.tablayout.util.DisplayUtil;
import com.zhaobaoge.widget.tablayout.util.TabFragmentManager;
import com.zhaobaoge.widget.tablayout.tabs.QTabView;
import com.zhaobaoge.widget.tablayout.tabs.TabView;

import java.util.ArrayList;
import java.util.List;


import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;


public class HorizontalTabLayout extends ScrollView implements ITabLayout {
    private Context mContext;
    private TabStrip mTabStrip;
    private int mColorIndicator;
    private TabView mSelectedTab;
    private int mTabMargin;
    private int mIndicatorHeight;
    private int mIndicatorGravity;
    private float mIndicatorCorners;
    private int mTabMode;
    private int mTabWidth;

    public static int TAB_MODE_FIXED = 10;
    public static int TAB_MODE_SCROLLABLE = 11;

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private TabAdapter mTabAdapter;

    private List<OnTabSelectedListener> mTabSelectedListeners;
    private OnTabPageChangeListener mTabPageChangeListener;
    private DataSetObserver mPagerAdapterObserver;

    private TabFragmentManager mTabFragmentManager;

    public HorizontalTabLayout(Context context) {
        this(context, null);
    }

    public HorizontalTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mTabSelectedListeners = new ArrayList<>();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalTabLayout);
        mColorIndicator = typedArray.getColor(R.styleable.HorizontalTabLayout_ht_indicator_color, context.getResources().getColor(R.color.colorAccent));
        mIndicatorHeight = (int) typedArray.getDimension(R.styleable.HorizontalTabLayout_ht_indicator_height, DisplayUtil.dp2px(context, 3));
        mIndicatorCorners = typedArray.getDimension(R.styleable.HorizontalTabLayout_ht_indicator_corners, 0);
        mIndicatorGravity = typedArray.getInteger(R.styleable.HorizontalTabLayout_ht_indicator_gravity, Gravity.LEFT);
        if (mIndicatorGravity == 3) {
            mIndicatorGravity = Gravity.LEFT;
        } else if (mIndicatorGravity == 5) {
            mIndicatorGravity = Gravity.RIGHT;
        } else if (mIndicatorGravity == 119) {
            mIndicatorGravity = Gravity.FILL;
        }
        mTabMargin = (int) typedArray.getDimension(R.styleable.HorizontalTabLayout_ht_tab_margin, 0);
        mTabMode = typedArray.getInteger(R.styleable.HorizontalTabLayout_ht_tab_mode, TAB_MODE_FIXED);
        int defaultTabWidth = LinearLayout.LayoutParams.WRAP_CONTENT;
        mTabWidth = (int) typedArray.getDimension(R.styleable.HorizontalTabLayout_ht_tab_width, defaultTabWidth);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) removeAllViews();
        initTabStrip();
    }

    private void initTabStrip() {
        mTabStrip = new TabStrip(mContext);
        addView(mTabStrip, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void removeAllTabs() {
        mTabStrip.removeAllViews();
        mSelectedTab = null;
    }

    public TabView getTabAt(int position) {
        return (TabView) mTabStrip.getChildAt(position);
    }

    public int getTabCount() {
        return mTabStrip.getChildCount();
    }

    public int getSelectedTabPosition() {
        int index = mTabStrip.indexOfChild(mSelectedTab);
        return index == -1 ? 0 : index;
    }

    private void addTabWithMode(TabView tabView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        initTabWithMode(params);
        mTabStrip.addView(tabView, params);
        if (mTabStrip.indexOfChild(tabView) == 0) {
            tabView.setChecked(true);
            params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            tabView.setLayoutParams(params);
            mSelectedTab = tabView;
            mTabStrip.post(new Runnable() {
                @Override
                public void run() {
                    mTabStrip.moveIndicator(0);
                }
            });
        }
    }

    private void initTabWithMode(LinearLayout.LayoutParams params) {
        if (mTabMode == TAB_MODE_FIXED) {
            params.height = 0;
            params.weight = 1.0f;
            params.setMargins(0, 0, 0, 0);
            setFillViewport(true);
        } else if (mTabMode == TAB_MODE_SCROLLABLE) {
            params.height = mTabWidth;
            params.weight = 0f;
            params.setMargins(0, mTabMargin, 0, 0);
            setFillViewport(false);
        }
    }

    private void scrollToTab(int position) {
        final TabView tabView = getTabAt(position);
        int y = getScrollY();
        int tabTop = tabView.getTop() + tabView.getHeight() / 2 - y;
        int target = getHeight() / 2;
        if (tabTop > target) {
            smoothScrollBy(0, tabTop - target);
        } else if (tabTop < target) {
            smoothScrollBy(0, tabTop - target);
        }
    }

    private float mLastPositionOffset;

    private void scrollByTab(int position, final float positionOffset) {
        final TabView tabView = getTabAt(position);
        int y = getScrollY();
        int tabTop = tabView.getTop() + tabView.getHeight() / 2 - y;
        int target = getHeight() / 2;
        int nextScrollY = tabView.getHeight() + mTabMargin;
        if (positionOffset > 0) {
            float percent = positionOffset - mLastPositionOffset;
            if (tabTop > target) {
                smoothScrollBy(0, (int) (nextScrollY * percent));
            }
        }
        mLastPositionOffset = positionOffset;
    }

    public void addTab(TabView tabView) {
        if (tabView != null) {
            addTabWithMode(tabView);
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = mTabStrip.indexOfChild(view);
                    setTabSelected(position);
                }
            });
        } else {
            throw new IllegalStateException("tabview can't be null");
        }
    }

    public void setTabSelected(final int position) {
        setTabSelected(position, true, true);
    }

    private void setTabSelected(final int position, final boolean updataIndicator, final boolean callListener) {
        post(new Runnable() {
            @Override
            public void run() {
                setTabSelectedImpl(position, updataIndicator, callListener);
            }
        });
    }

    private void setTabSelectedImpl(final int position, boolean updataIndicator, boolean callListener) {
        TabView view = getTabAt(position);
        boolean selected;
        if (selected = (view != mSelectedTab)) {
            if (mSelectedTab != null) {
                mSelectedTab.setChecked(false);
            }
            view.setChecked(true);
            if (updataIndicator) {
                mTabStrip.moveIndicatorWithAnimator(position);
            }
            mSelectedTab = view;
            scrollToTab(position);
        }
        if (callListener) {
            for (int i = 0; i < mTabSelectedListeners.size(); i++) {
                OnTabSelectedListener listener = mTabSelectedListeners.get(i);
                if (listener != null) {
                    if (selected) {
                        listener.onTabSelected(view, position);
                    } else {
                        listener.onTabReselected(view, position);
                    }
                }
            }
        }
    }


    public void setTabMode(int mode) {
        if (mode != TAB_MODE_FIXED && mode != TAB_MODE_SCROLLABLE) {
            throw new IllegalStateException("only support TAB_MODE_FIXED or TAB_MODE_SCROLLABLE");
        }
        if (mode == mTabMode) return;
        mTabMode = mode;
        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
            View view = mTabStrip.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            initTabWithMode(params);
            if (i == 0) {
                params.setMargins(0, 0, 0, 0);
            }
            view.setLayoutParams(params);
        }
        mTabStrip.invalidate();
        mTabStrip.post(new Runnable() {
            @Override
            public void run() {
                mTabStrip.updataIndicator();
            }
        });
    }

    /**
     * only in TAB_MODE_SCROLLABLE mode will be supported
     *
     * @param margin margin
     */
    public void setTabMargin(int margin) {
        if (margin == mTabMargin) return;
        mTabMargin = margin;
        if (mTabMode == TAB_MODE_FIXED) return;
        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
            View view = mTabStrip.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(0, i == 0 ? 0 : mTabMargin, 0, 0);
            view.setLayoutParams(params);
        }
        mTabStrip.invalidate();
        mTabStrip.post(new Runnable() {
            @Override
            public void run() {
                mTabStrip.updataIndicator();
            }
        });
    }

    /**
     * only in TAB_MODE_SCROLLABLE mode will be supported
     *
     * @param width height
     */
    public void setTabWidth(int width) {
        if (width == mTabWidth) return;
        mTabWidth = width;
        if (mTabMode == TAB_MODE_FIXED) return;
        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
            View view = mTabStrip.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.width = mTabWidth;
            view.setLayoutParams(params);
        }
        mTabStrip.invalidate();
        mTabStrip.post(new Runnable() {
            @Override
            public void run() {
                mTabStrip.updataIndicator();
            }
        });
    }

    public void setIndicatorColor(int color) {
        mColorIndicator = color;
        mTabStrip.invalidate();
    }

    public void setIndicatorHeight(int height) {
        mIndicatorHeight = height;
    }

    public void setIndicatorCorners(int corners) {
        mIndicatorCorners = corners;
        mTabStrip.invalidate();
    }

    /**
     * @param gravity only support Gravity.LEFT,Gravity.RIGHT,Gravity.FILL
     */
    public void setIndicatorGravity(int gravity) {
        if (gravity == Gravity.LEFT || gravity == Gravity.RIGHT || Gravity.FILL == gravity) {
            mIndicatorGravity = gravity;
        } else {
            throw new IllegalStateException("only support Gravity.LEFT,Gravity.RIGHT,Gravity.FILL");
        }
    }


    public void addOnTabSelectedListener(OnTabSelectedListener listener) {
        if (listener != null) {
            mTabSelectedListeners.add(listener);
        }
    }

    public void removeOnTabSelectedListener(OnTabSelectedListener listener) {
        if (listener != null) {
            mTabSelectedListeners.remove(listener);
        }
    }

    public void setTabAdapter(TabAdapter adapter) {
        removeAllTabs();
        if (adapter != null) {
            mTabAdapter = adapter;
            for (int i = 0; i < adapter.getCount(); i++) {
                addTab(new QTabView(mContext) {
                }.setIcon(adapter.getIcon(i))
                        .setTitle(adapter.getTitle(i))
                        .setBackground(adapter.getBackground(i)));
            }
        }
    }

    public void setupWithFragment(FragmentManager manager, List<Fragment> fragments) {
        setupWithFragment(manager, 0, fragments);
    }

    public void setupWithFragment(FragmentManager manager, List<Fragment> fragments, TabAdapter adapter) {
        setupWithFragment(manager, 0, fragments, adapter);
    }

    public void setupWithFragment(FragmentManager manager, int containerResid, List<Fragment> fragments) {
        if (mTabFragmentManager != null) {
            mTabFragmentManager.detach();
        }
        if (containerResid != 0) {
            mTabFragmentManager = new TabFragmentManager(manager, containerResid, fragments, this);
        } else {
            mTabFragmentManager = new TabFragmentManager(manager, fragments, this);
        }
    }

    public void setupWithFragment(FragmentManager manager, int containerResid, List<Fragment> fragments, TabAdapter adapter) {
        setTabAdapter(adapter);
        setupWithFragment(manager, containerResid, fragments);
    }

    public void setViewPager(@Nullable ViewPager viewPager) {
        if (mViewPager != null && mTabPageChangeListener != null) {
            mViewPager.removeOnPageChangeListener(mTabPageChangeListener);
        }

        if (viewPager != null) {
            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter == null) {
                throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
            }

            mViewPager = viewPager;

            if (mTabPageChangeListener == null) {
                mTabPageChangeListener = new OnTabPageChangeListener();
            }
            viewPager.addOnPageChangeListener(mTabPageChangeListener);

            addOnTabSelectedListener(new OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabView tab, int position) {
                    if (mViewPager != null && mViewPager.getAdapter().getCount() >= position) {
                        mViewPager.setCurrentItem(position);
                    }
                }

                @Override
                public void onTabReselected(TabView tab, int position) {
                }
            });

            setPagerAdapter(adapter, true);
        } else {
            mViewPager = null;
            setPagerAdapter(null, true);
        }
    }

    private void setPagerAdapter(@Nullable final PagerAdapter adapter, final boolean addObserver) {
        if (mPagerAdapter != null && mPagerAdapterObserver != null) {
            mPagerAdapter.unregisterDataSetObserver(mPagerAdapterObserver);
        }

        mPagerAdapter = adapter;

        if (addObserver && adapter != null) {
            if (mPagerAdapterObserver == null) {
                mPagerAdapterObserver = new PagerAdapterObserver();
            }
            adapter.registerDataSetObserver(mPagerAdapterObserver);
        }

        populateFromPagerAdapter();
    }

    private void populateFromPagerAdapter() {
        removeAllTabs();
        if (mPagerAdapter != null) {
            final int adapterCount = mPagerAdapter.getCount();
            if (mPagerAdapter instanceof TabAdapter) {
                setTabAdapter((TabAdapter) mPagerAdapter);
            } else {
                for (int i = 0; i < adapterCount; i++) {
                    String title = mPagerAdapter.getPageTitle(i) == null ? "tab" + i : mPagerAdapter.getPageTitle(i).toString();
                    addTab(new QTabView(mContext).setTitle(new QTabView.TabTitle.Builder().setContent(title).build()));
                }
            }

            // Make sure we reflect the currently set ViewPager item
            if (mViewPager != null && adapterCount > 0) {
                final int curItem = mViewPager.getCurrentItem();
                if (curItem != getSelectedTabPosition() && curItem < getTabCount()) {
                    setTabSelected(curItem);
                }
            }
        } else {
            removeAllTabs();
        }
    }

    private class TabStrip extends LinearLayout {
        private float mIndicatorLeft;
        private float mIndicatorRight;
        private int mLastWidth;
        private Paint mIndicatorPaint;
        private RectF mIndicatorRect;
        private AnimatorSet mIndicatorAnimatorSet;

        public TabStrip(Context context) {
            super(context);
            setWillNotDraw(false);
            setOrientation(LinearLayout.HORIZONTAL);
            mIndicatorPaint = new Paint();
            mIndicatorPaint.setAntiAlias(true);
            mIndicatorGravity = mIndicatorGravity == 0 ? Gravity.LEFT : mIndicatorGravity;
            mIndicatorRect = new RectF();
        }

        private void calcIndicatorX(float offset) {
            int index = (int) Math.floor(offset);
            View childView = getChildAt(index);
            if (Math.floor(offset) != getChildCount() - 1 && Math.ceil(offset) != 0) {
                View nextView = getChildAt(index + 1);
                final float nextTabLeft = nextView.getLeft();
                final float nextTabRight = nextView.getRight();

                mIndicatorLeft = (offset * nextTabLeft + (1f - offset) * mIndicatorLeft);
                mIndicatorRight = (offset * nextTabRight + (1f - offset) * mIndicatorRight);
            } else {
                mIndicatorLeft = childView.getLeft();
                mIndicatorRight = childView.getRight();
            }
        }

        protected void updataIndicator() {
            moveIndicatorWithAnimator(getSelectedTabPosition());
        }

        protected void moveIndicator(float offset) {
            calcIndicatorX(offset);
            invalidate();
        }

        /**
         * move indicator to a tab location
         *
         * @param index tab location's index
         */
        protected void moveIndicatorWithAnimator(int index) {
            final int direction = index - getSelectedTabPosition();
            View childView = getChildAt(index);
            final float targetTop = childView.getTop();
            final float targetBottom = childView.getBottom();
            if (mIndicatorLeft == targetTop && mIndicatorRight == targetBottom) return;
            if (mIndicatorAnimatorSet != null && mIndicatorAnimatorSet.isRunning()) {
                mIndicatorAnimatorSet.end();
            }
            post(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator startAnime = null;
                    ValueAnimator endAnime = null;
                    if (direction > 0) {
                        startAnime = ValueAnimator.ofFloat(mIndicatorRight, targetBottom)
                                .setDuration(100);
                        startAnime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mIndicatorRight = Float.parseFloat(animation.getAnimatedValue().toString());
                                invalidate();
                            }
                        });
                        endAnime = ValueAnimator.ofFloat(mIndicatorLeft, targetTop).setDuration(100);
                        endAnime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mIndicatorLeft = Float.parseFloat(animation.getAnimatedValue().toString());
                                invalidate();
                            }
                        });
                    } else if (direction < 0) {
                        startAnime = ValueAnimator.ofFloat(mIndicatorLeft, targetTop).setDuration(100);
                        startAnime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mIndicatorLeft = Float.parseFloat(animation.getAnimatedValue().toString());
                                invalidate();
                            }
                        });
                        endAnime = ValueAnimator.ofFloat(mIndicatorRight, targetBottom).setDuration(100);
                        endAnime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mIndicatorRight = Float.parseFloat(animation.getAnimatedValue().toString());
                                invalidate();
                            }
                        });
                    }
                    if (startAnime != null) {
                        mIndicatorAnimatorSet = new AnimatorSet();
                        mIndicatorAnimatorSet.play(endAnime).after(startAnime);
                        mIndicatorAnimatorSet.start();
                    }
                }
            });
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            mIndicatorPaint.setColor(mColorIndicator);
            mIndicatorRect.left = mIndicatorLeft;
            mIndicatorRect.top = getHeight() - mIndicatorHeight;
            mIndicatorRect.right = mIndicatorRight;
            mIndicatorRect.bottom = getHeight();
            if (mIndicatorCorners != 0) {
                canvas.drawRoundRect(mIndicatorRect, mIndicatorCorners, mIndicatorCorners, mIndicatorPaint);
            } else {
                canvas.drawRect(mIndicatorRect, mIndicatorPaint);
            }
        }

    }

    private class OnTabPageChangeListener implements ViewPager.OnPageChangeListener {
        private int mPreviousScrollState;
        private int mScrollState;
        boolean mUpdataIndicator;

        public OnTabPageChangeListener() {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
            mUpdataIndicator = !(mScrollState == SCROLL_STATE_SETTLING
                    && mPreviousScrollState == SCROLL_STATE_IDLE);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            if (mUpdataIndicator) {
                mTabStrip.moveIndicator(positionOffset + position);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (position != getSelectedTabPosition()) {
                setTabSelected(position, !mUpdataIndicator, true);
            }
        }
    }

    private class PagerAdapterObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            populateFromPagerAdapter();
        }

        @Override
        public void onInvalidated() {
            populateFromPagerAdapter();
        }
    }


}
