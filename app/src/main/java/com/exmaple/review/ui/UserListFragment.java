package com.exmaple.review.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.exmaple.review.MainViewModel;
import com.exmaple.review.R;
import com.exmaple.review.databinding.ItemUserBinding;
import com.exmaple.review.models.User;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainViewModel viewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        UserAdapter adapter = new UserAdapter(model -> {
            UserListFragmentDirections
                    .ActionUserListFragmentToAlbumListFragment action = UserListFragmentDirections.actionUserListFragmentToAlbumListFragment(model.getId());

            Navigation.findNavController(view).navigate(action);
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);


        // User 관찰, UI 업데이트
        viewModel.users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setItems(users);
            }
        });

        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        viewModel.isProgressing.observe(this, isProgressing -> {
            if (isProgressing) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        // User 데이터 호출
        viewModel.fetchUsers();

        viewModel.errorMessage.observe(this, error -> Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show());
    }

    private static class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
        interface OnUserClickListener {
            void onUserClick(User model);
        }

        private OnUserClickListener mListener;

        private List<User> mItems = new ArrayList<>();

        public UserAdapter() {
        }

        public UserAdapter(OnUserClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<User> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user, parent, false);
            final UserHolder viewHolder = new UserHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final User item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.onUserClick(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            User item = mItems.get(position);
            holder.binding.setUser(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class UserHolder extends RecyclerView.ViewHolder {
            ItemUserBinding binding;

            public UserHolder(@NonNull View itemView) {
                super(itemView);
                binding = ItemUserBinding.bind(itemView);
            }
        }
    }
}
