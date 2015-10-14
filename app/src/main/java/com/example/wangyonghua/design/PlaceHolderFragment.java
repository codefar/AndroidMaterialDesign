package com.example.wangyonghua.design;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PlaceHolderFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private RecyclerView mRecyclerView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private PlaceHolderViewAdapter mAdapter;

    public static PlaceHolderFragment newInstance() {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaceHolderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mAdapter = new PlaceHolderViewAdapter(mRecyclerView.getContext());
        mRecyclerView.setAdapter(mAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    private static class PlaceHolderViewAdapter extends RecyclerView.Adapter<PlaceHolderViewAdapter.ViewHolder>{

        private final TypedValue mTypedValue = new TypedValue();

        public PlaceHolderViewAdapter(Context context){
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_placeholder_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            String url = Constants.IMAGES[i];
            String name = "##";
            if (!TextUtils.isEmpty(url)) {
                String p[] = url.split("/");
                if (p != null && p.length > 0);
                    name = p[p.length - 1];
            }
            viewHolder.mTextView.setText(name);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    context.startActivity(new Intent(context,ImageDetailActivity.class));
                }
            });

            Glide.with(viewHolder.mImageView.getContext())
                    .load(Constants.IMAGES[i])
                    .fitCenter()
                    .into(viewHolder.mImageView);
        }

        @Override
        public int getItemCount() {
            return Constants.IMAGES.length;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                view.setBackgroundResource(mTypedValue.resourceId);
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }
    }

    public void setFragmentInteractionListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }
}
