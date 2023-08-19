package hn.uth.examen201830020043.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.databinding.ContactItemBinding;
import hn.uth.examen201830020043.databinding.ContactLugarItemBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contacto> dataset;
    private OnItemClickListener<Contacto> listener;
    public ContactAdapter(List<Contacto> dataset, OnItemClickListener<Contacto> listener){
        this.dataset = dataset;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactLugarItemBinding binding = ContactLugarItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacto contact = dataset.get(position);
        holder.getBinding().tvContactNombre.setText(contact.getNombre());
        holder.getBinding().tvContactTelefono.setText(contact.getTelefono());
        holder.setOnclickListener(contact,listener,position);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void setItems(List<Contacto> dataset){
        this.dataset = dataset;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ContactLugarItemBinding binding;
        public ViewHolder(@NonNull ContactLugarItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void setOnclickListener(Contacto emergyContact, OnItemClickListener<Contacto> listener,int posicion){
            binding.btnContactDelete.setOnClickListener(e -> listener.onItemClick(emergyContact,posicion));
        }
        public ContactLugarItemBinding getBinding(){
            return binding;
        }
    }
}
