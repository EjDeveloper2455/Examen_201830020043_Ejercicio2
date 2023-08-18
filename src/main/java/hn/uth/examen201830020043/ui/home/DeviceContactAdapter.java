package hn.uth.examen201830020043.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.examen201830020043.databinding.ContactItemBinding;
import hn.uth.examen201830020043.DataBase.Entities.DeviceContact;
import hn.uth.examen201830020043.ui.OnItemClickListener;

public class DeviceContactAdapter extends RecyclerView.Adapter<DeviceContactAdapter.ViewHolder>{

        private List<DeviceContact> dataset;
        private OnItemClickListener<DeviceContact> listener;
        public DeviceContactAdapter(List<DeviceContact> dataset, OnItemClickListener<DeviceContact> listener){
            this.dataset = dataset;
            this.listener = listener;
        }

        @NonNull
        @Override
        public DeviceContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ContactItemBinding binding = ContactItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new DeviceContactAdapter.ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull DeviceContactAdapter.ViewHolder holder, int position) {
            DeviceContact contact = dataset.get(position);
            holder.getBinding().tvContactName.setText(contact.getNombre());
            holder.getBinding().tvContactPhone.setText(contact.getTelefono());
            holder.setOnclickListener(contact,listener);
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }
        public void setItems(List<DeviceContact> dataset){
            this.dataset = dataset;
            notifyDataSetChanged();
        }
        public class ViewHolder extends RecyclerView.ViewHolder{
            private ContactItemBinding binding;
            public ViewHolder(@NonNull ContactItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
            public void setOnclickListener(DeviceContact deviceContact, OnItemClickListener<DeviceContact> listener){
                binding.btnContactAdd.setOnClickListener(e -> listener.onItemClick(deviceContact,0));
            }
            public ContactItemBinding getBinding(){
                return binding;
            }
        }
    }

