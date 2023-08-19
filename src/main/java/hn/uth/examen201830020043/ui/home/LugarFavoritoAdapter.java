package hn.uth.examen201830020043.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.databinding.LugarFavoritoItemBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;

public class LugarFavoritoAdapter extends RecyclerView.Adapter<LugarFavoritoAdapter.ViewHolder> {

    private List<LugarFavorito> dataset;
    private OnItemClickListener<LugarFavorito> onItemClickListener;

    public LugarFavoritoAdapter(List<LugarFavorito> dataset, OnItemClickListener<LugarFavorito> onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LugarFavoritoItemBinding binding = LugarFavoritoItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LugarFavorito lugarFavorito = dataset.get(position);
        holder.binding.tvNombreLugar.setText(lugarFavorito.getNombre());
        holder.binding.tvTipoLugar.setText(lugarFavorito.getTipo());
        holder.setOnItemClickListener(lugarFavorito,onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setItems(List<LugarFavorito> list){
        this.dataset = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LugarFavoritoItemBinding binding;
        public ViewHolder(LugarFavoritoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setOnItemClickListener(LugarFavorito lugarFavorito, OnItemClickListener<LugarFavorito> listener){
            binding.imgAbrirMapaLugar.setOnClickListener(e ->listener.onItemClick(lugarFavorito,2));
            binding.imgEditLugar.setOnClickListener(e ->listener.onItemClick(lugarFavorito,1));
            binding.imgDeleteLugar.setOnClickListener(e ->listener.onItemClick(lugarFavorito,0));
            binding.imgVerLugar.setOnClickListener(e ->listener.onItemClick(lugarFavorito,3));
            binding.imgAddVisita.setOnClickListener(e ->listener.onItemClick(lugarFavorito,4));
        }
    }
}
