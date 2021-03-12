package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaceholderFragment extends Fragment {
    private FriendAdapter friendAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Friend> friends = new ArrayList<Friend>();
    private View lottie;



//    @Override
//    public void onStart() {
//        friends = Friend.getItems();
//        super.onStart();
//    }

    public void getFriends(int number) {
        for (int i = 0; i < number; ++i) {
            friends.add(new Friend("Friend" + i, 1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        getFriends(10);
        lottie = view.findViewById(R.id.lottie_view);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        friendAdapter = new FriendAdapter(friends);
        recyclerView.setAdapter(friendAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator lottie_out = ObjectAnimator.ofFloat(lottie, "alpha",
                        1.0f, 0f);
                lottie_out.setDuration(1000);
                lottie_out.setInterpolator(new LinearInterpolator());

                ObjectAnimator recycler_in = ObjectAnimator.ofFloat(recyclerView,
                        "alpha",0f, 1.0f);
                recycler_in.setDuration(1000);
                recycler_in.setInterpolator(new LinearInterpolator());

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(lottie_out, recycler_in);
                animatorSet.start();
            }
        }, 5000);
    }
}
