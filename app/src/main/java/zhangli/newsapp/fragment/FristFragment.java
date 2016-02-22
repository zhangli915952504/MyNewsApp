package zhangli.newsapp.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.Indicators.PagerIndicator;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.warmtel.android.xlistview.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import zhangli.newsapp.R;
import zhangli.newsapp.news.News;
import zhangli.newsapp.news.TAll;
import zhangli.newsapp.news.imgextra;

public class FristFragment extends Fragment {
    private XListView xlistview;
    private SliderLayout mSliderLayout;
    private ProgressBar progressBar;
    private String news_Url="http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
    private RelativeLayout relativeLayout;
    private MyAdapter mAdapter;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public static FristFragment newInstance() {
        FristFragment fragment = new FristFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar = (ProgressBar) getView().findViewById(R.id.merchant_progressbar);
        //最上面滑动的大图。
        View sliderView = LayoutInflater.from(getActivity()).inflate(R.layout.silider_image, null);
        mSliderLayout = (SliderLayout) sliderView.findViewById(R.id.slider_imager);
        xlistview= (XListView) getView().findViewById(R.id.xlistview);
        //加载的圆圈
        relativeLayout = (RelativeLayout) getView().findViewById(R.id.main_relativelayout_id);
        //添加到ListView的头部
        xlistview.addHeaderView(sliderView);

        //----------------上面滑动的大图添加数据--------------------------------------------------------
        HashMap<String, String> sliderList = getImageSlider();
        for (final String key : sliderList.keySet()) {
            String url = sliderList.get(key);
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(key);
            textSliderView.image(url);
            //对图片进行中心裁切  不压缩图片
            textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
            //给滑动图片安上点击事件，点击就吐丝key.
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {

                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.makeText(getActivity(), key, Toast.LENGTH_SHORT).show();
                }
            });
            mSliderLayout.addSlider(textSliderView);
        }

        //将小球指示器放在右下角。
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSliderLayout.setClickable(true);
        //自定义的小球
        mSliderLayout.setCustomIndicator((PagerIndicator) getView().findViewById(R.id.custom_pagerindicator));

        mAdapter = new MyAdapter(getActivity());
        xlistview.setAdapter(mAdapter);
        xlistview.setEmptyView(progressBar);

        getAsyscDataList();

        //下拉刷新功能
        xlistview.setPullLoadEnable(true);
        xlistview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        getAsyscDataList();
                    }
                }.execute();
            }

            @Override
            public void onLoadMore() {
                getAsyscDataList();
                Toast.makeText(getActivity(), "还没实现查看更多的功能", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public HashMap<String, String> getImageSlider() {
        HashMap<String, String> image = new HashMap<String, String>();
        image.put("广西警方发扑克牌通缉令 印248名逃犯", "http://img4.cache.netease.com/3g/2016/1/11/20160111110234e9d03.jpg");
        image.put("墨西哥足球队员及家属大巴坠桥18人死", "http://img4.cache.netease.com/3g/2016/1/11/20160111110234e9d03.jpg");
        image.put("韩国举办大型冰钓比赛 场面壮观", "http://img2.cache.netease.com/3g/2016/1/11/20160111100925f0ac3.jpg");
        image.put("金球奖红毯:詹妮弗·洛佩兹搞怪", "http://img3.cache.netease.com/3g/2016/1/11/201601111154270a423.jpg");
        image.put("重庆现\"任性\"斑马线 要过需先翻栏杆", "http://img3.cache.netease.com/3g/2016/1/11/201601111038599bb1b.jpg");
        return image;
    }

    public void getAsyscDataList() {
        asyncHttpClient.get(news_Url, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(getActivity(), "正在加载中。。。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Gson gson = new Gson();
                News news = gson.fromJson(s, News.class);
                List<TAll> tall=news.getT1348647909107();

                mAdapter.setList(tall);

                if (relativeLayout != null) {
                    relativeLayout.setVisibility(View.GONE);
                    relativeLayout = null;
                }

                xlistview.setRefreshTime(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis()));
                xlistview.stopRefresh();
                xlistview.stopLoadMore();
            }
        });
    }


    public static class MyAdapter extends BaseAdapter {
        private static final int TYPE_ONE = 0;
        private static final int TYPE_TWO = 1;
        private static final int TYPE_THREE = 2;


        private List<TAll> list = new ArrayList<>();
        private Context context;
        private LayoutInflater inflater;

        private MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void setList(List<TAll> tList) {
            list = tList;
            //通知刷新适配器数据源
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            TAll TAll = (TAll) getItem(position);
            if (TAll.getImgextra() != null) {
                //三张图的
                return TYPE_TWO;
            } else if (TAll.getSkipType() != null && TAll.getSkipType().equals("special")) {
                //有专题的
                return TYPE_ONE;
            } else {
                //普通的
                return TYPE_THREE;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (getItemViewType(position) == TYPE_ONE) {
                return getView_one(position, convertView, parent);
            } else if (getItemViewType(position) == TYPE_TWO) {
                return getView_two(position, convertView, parent);
            } else {
                return getView_three(position, convertView, parent);
            }
        }

        public View getView_one(int position, View convertView, ViewGroup parent) {
            ViewHodler viewHodler = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.news_item_layout, null);
                viewHodler = new ViewHodler();
                viewHodler.image_pic = (ImageView) convertView.findViewById(R.id.imageview);
                viewHodler.text_title = (TextView) convertView.findViewById(R.id.textview_title);
                viewHodler.text_digest = (TextView) convertView.findViewById(R.id.textview_digest);
                viewHodler.replyCount = (TextView) convertView.findViewById(R.id.number_textview);
                viewHodler.skipType = (TextView) convertView.findViewById(R.id.textview_special);
                convertView.setTag(viewHodler);
            }
            viewHodler = (ViewHodler) convertView.getTag();
            TAll TAll = (TAll) getItem(position);
            Picasso.with(context).load(TAll.getImgsrc()).into(viewHodler.image_pic);
            viewHodler.text_title.setText(TAll.getTitle());
            viewHodler.text_digest.setText(TAll.getDigest());
            viewHodler.replyCount.setText((Integer.toString(TAll.getReplyCount())));
            return convertView;
        }

        public View getView_two(int position, View convertView, ViewGroup parent) {
            ViewHodler viewHodler = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.news_item_layout_two, null);
                viewHodler = new ViewHodler();
                viewHodler.image_pic = (ImageView) convertView.findViewById(R.id.imageview);
                viewHodler.image_pic2 = (ImageView) convertView.findViewById(R.id.imageview2);
                viewHodler.image_pic3 = (ImageView) convertView.findViewById(R.id.imageview3);
                viewHodler.text_title = (TextView) convertView.findViewById(R.id.textview_title);
                convertView.setTag(viewHodler);
            }
            viewHodler = (ViewHodler) convertView.getTag();
            TAll TAll = (TAll) getItem(position);
            Picasso.with(context).load(TAll.getImgsrc()).into(viewHodler.image_pic);
            viewHodler.text_title.setText(TAll.getTitle());
            //图2图3
            List<imgextra> imgextra = TAll.getImgextra();
            Picasso.with(context).load(imgextra.get(0).getImgsrc()).into(viewHodler.image_pic2);
            Picasso.with(context).load(imgextra.get(1).getImgsrc()).into(viewHodler.image_pic3);
            return convertView;
        }

        public View getView_three(int position, View convertView, ViewGroup parent) {
            ViewHodler viewHodler = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.news_item_layout_three, null);
                viewHodler = new ViewHodler();
                viewHodler.image_pic = (ImageView) convertView.findViewById(R.id.imageview);
                viewHodler.text_title = (TextView) convertView.findViewById(R.id.textview_title);
                viewHodler.text_digest = (TextView) convertView.findViewById(R.id.textview_digest);
                viewHodler.replyCount = (TextView) convertView.findViewById(R.id.number_textview);
                viewHodler.skipType = (TextView) convertView.findViewById(R.id.textview_special);
                convertView.setTag(viewHodler);
            }
            viewHodler = (ViewHodler) convertView.getTag();
            TAll TAll = (TAll) getItem(position);
            Picasso.with(context).load(TAll.getImgsrc()).into(viewHodler.image_pic);
            viewHodler.text_title.setText(TAll.getTitle());
            viewHodler.text_digest.setText(TAll.getDigest());
            viewHodler.replyCount.setText((Integer.toString(TAll.getReplyCount())));
            return convertView;
        }

        public class ViewHodler {
            ImageView image_pic;
            ImageView image_pic2;
            ImageView image_pic3;
            TextView text_title;
            TextView text_digest;
            TextView replyCount;
            TextView skipType;

        }
    }


}
