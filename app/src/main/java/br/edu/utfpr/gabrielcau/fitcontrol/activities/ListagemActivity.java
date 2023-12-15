package br.edu.utfpr.gabrielcau.fitcontrol.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.edu.utfpr.gabrielcau.fitcontrol.R;
import br.edu.utfpr.gabrielcau.fitcontrol.adapters.ExercicioAdapter;
import br.edu.utfpr.gabrielcau.fitcontrol.entities.Exercicio;
import androidx.appcompat.app.AppCompatActivity;

public class ListagemActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CADASTRO = 1;
    private static final int REQUEST_CODE_EDICAO = 2;

    private ListView lvExercicios;
    private ArrayList<Exercicio> exercicios;
    private ExercicioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        lvExercicios = findViewById(R.id.lvExercicios);
        exercicios = new ArrayList<>();
        gerarExerciciosDeExemplo();
        adapter = new ExercicioAdapter(this, exercicios);
        lvExercicios.setAdapter(adapter);

        registerForContextMenu(lvExercicios);

        lvExercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editarExercicio(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_adicionar) {
            startActivityForResult(new Intent(this, CadastroActivity.class), REQUEST_CODE_CADASTRO);
            return true;
        } else if (id == R.id.action_sobre) {
            startActivity(new Intent(this, AutoriaActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual_listagem, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if (item.getItemId() == R.id.action_editar) {
            editarExercicio(position);
            return true;
        } else if (item.getItemId() == R.id.action_excluir) {
            exercicios.remove(position);
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && (requestCode == REQUEST_CODE_CADASTRO || requestCode == REQUEST_CODE_EDICAO)) {
            Exercicio exercicio = (Exercicio) data.getSerializableExtra("exercicio");
            if (requestCode == REQUEST_CODE_CADASTRO) {
                exercicios.add(exercicio);
            } else if (requestCode == REQUEST_CODE_EDICAO) {
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    exercicios.set(position, exercicio);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
    private void gerarExerciciosDeExemplo() {
        int[] nomesExercicios = {
                R.string.exercicio_corrida,
                R.string.exercicio_natacao,
                R.string.exercicio_ciclismo,
                R.string.exercicio_pular_corda,
                R.string.exercicio_danca,
                R.string.exercicio_agachamento,
                R.string.exercicio_flexao_de_braco,
                R.string.exercicio_abdominais,
                R.string.exercicio_levantamento_de_peso,
                R.string.exercicio_barra_fixa
        };

        int[] tiposExercicios = {
                R.string.exercicio_tipo_aerobico,
                R.string.exercicio_tipo_aerobico,
                R.string.exercicio_tipo_aerobico,
                R.string.exercicio_tipo_aerobico,
                R.string.exercicio_tipo_aerobico,
                R.string.exercicio_tipo_anaerobico,
                R.string.exercicio_tipo_anaerobico,
                R.string.exercicio_tipo_anaerobico,
                R.string.exercicio_tipo_anaerobico,
                R.string.exercicio_tipo_anaerobico
        };

        int[] duracoesExercicios = {
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10
        };

        for (int i = 0; i < 10; i++) {
            Exercicio exercicio = new Exercicio(getString(nomesExercicios[i]), getString(tiposExercicios[i]), false, duracoesExercicios[i]);
            exercicios.add(exercicio);
        }
    }

    private void editarExercicio(int position) {
        Intent intent = new Intent(this, CadastroActivity.class);
        intent.putExtra("exercicio", exercicios.get(position));
        intent.putExtra("position", position);
        startActivityForResult(intent, REQUEST_CODE_EDICAO);
    }
}
