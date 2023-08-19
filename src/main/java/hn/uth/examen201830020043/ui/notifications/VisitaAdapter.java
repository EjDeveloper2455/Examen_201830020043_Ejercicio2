package hn.uth.examen201830020043.ui.notifications;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.Visita;
import hn.uth.examen201830020043.databinding.ContactLugarItemBinding;
import hn.uth.examen201830020043.databinding.VisitaItemBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;

public class VisitaAdapter extends RecyclerView.Adapter<VisitaAdapter.ViewHolder> {
    private List<Visita> dataset;
    private OnItemClickListener<Visita> listener;
    public VisitaAdapter(List<Visita> dataset, OnItemClickListener<Visita> listener){
        this.dataset = dataset;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VisitaItemBinding binding = VisitaItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Visita visita = dataset.get(position);
        holder.getBinding().tvVisitaNombre.setText(visita.getNombre());
        holder.getBinding().tvVisitaFecha.setText(visita.getFecha());
        holder.setOnclickListener(visita,listener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void setItems(List<Visita> dataset){
        this.dataset = dataset;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private VisitaItemBinding binding;
        public ViewHolder(@NonNull VisitaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void setOnclickListener(Visita emergyContact, OnItemClickListener<Visita> listener){
            binding.imgEliminarVisita.setOnClickListener(e -> listener.onItemClick(emergyContact,0));
            binding.imgVerContactosVisita.setOnClickListener(e -> listener.onItemClick(emergyContact,1));
        }
        public VisitaItemBinding getBinding(){
            return binding;
        }
    }
}
