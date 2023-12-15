package br.edu.utfpr.gabrielcau.fitcontrol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.utfpr.gabrielcau.fitcontrol.R;
import br.edu.utfpr.gabrielcau.fitcontrol.entities.Exercicio;

public class ExercicioAdapter extends ArrayAdapter<Exercicio> {

    private final Context context;
    private final ArrayList<Exercicio> exercicios;

    public ExercicioAdapter(Context context, ArrayList<Exercicio> exercicios) {
        super(context, R.layout.list_item_exercicio, exercicios);
        this.context = context;
        this.exercicios = exercicios;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_exercicio, parent, false);

        TextView tvNome = rowView.findViewById(R.id.tvNome);
        TextView tvTipo = rowView.findViewById(R.id.tvTipo);
        TextView tvConcluido = rowView.findViewById(R.id.tvConcluido);
        TextView tvDuracao = rowView.findViewById(R.id.tvDuracao);

        Exercicio exercicio = exercicios.get(position);
        tvNome.setText(exercicio.getNome());
        tvTipo.setText(exercicio.getTipo());
        tvConcluido.setText(exercicio.isConcluido() ? context.getString(R.string.concluido) : context.getString(R.string.nao_concluido));
        tvDuracao.setText(String.format("%d min", exercicio.getDuracao()));

        return rowView;
    }
}
