package zhangli.newsapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuFragment extends Fragment {
    private Button fragment_merchant_login_btn;
    private Button fragment_merchant_collect_btn;
    private Button fragment_merchant_set_btn;
    private Button fragment_merchant_out_btn;
    private OnFragmentInterfaceListener mlistener;

    public interface OnFragmentInterfaceListener {
        void toLogin();

        void toCollect();

        void toSet();

        void toOut();
    }

    //加载fragment时调用
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInterfaceListener) {
            mlistener = (OnFragmentInterfaceListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMenuFragmentInteractionListener");
        }
    }

    public static MenuFragment newInstance() {
        MenuFragment menuFragment = new MenuFragment();
        return menuFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment_merchant_login_btn = (Button) getView().findViewById(R.id.fragment_merchant_login_btn);
        fragment_merchant_collect_btn = (Button) getView().findViewById(R.id.fragment_merchant_collect_btn);
        fragment_merchant_set_btn = (Button) getView().findViewById(R.id.fragment_merchant_set_btn);
        fragment_merchant_out_btn = (Button) getView().findViewById(R.id.fragment_merchant_out_btn);

        fragment_merchant_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.toLogin();

            }
        });

        fragment_merchant_collect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.toCollect();

            }
        });


        fragment_merchant_set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.toSet();
            }
        });

        fragment_merchant_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.toOut();
            }
        });
    }


}
