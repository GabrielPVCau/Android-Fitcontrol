package br.edu.utfpr.gabrielcau.fitcontrol.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.gabrielcau.fitcontrol.R;
import br.edu.utfpr.gabrielcau.fitcontrol.entities.Exercicio;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome;
    private RadioGroup rgTipo;
    private CheckBox cbConcluido;
    private Spinner spDuracao;

    private Exercicio exercicioParaEditar;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etNome = findViewById(R.id.etNome);
        rgTipo = findViewById(R.id.rgTipo);
        cbConcluido = findViewById(R.id.cbConcluido);
        spDuracao = findViewById(R.id.spDuracao);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.duracao_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDuracao.setAdapter(adapter);

        exercicioParaEditar = (Exercicio) getIntent().getSerializableExtra("exercicio");
        position = getIntent().getIntExtra("position", -1);

        if (exercicioParaEditar != null) {
            etNome.setText(exercicioParaEditar.getNome());

            for (int i = 0; i < rgTipo.getChildCount(); i++) {
                RadioButton rb = (RadioButton) rgTipo.getChildAt(i);
                if (rb.getText().toString().equals(exercicioParaEditar.getTipo())) {
                    rgTipo.check(rb.getId());
                    break;
                }
            }

            cbConcluido.setChecked(exercicioParaEditar.isConcluido());

            int duracaoPosition = 0;
            for (int i = 0; i < spDuracao.getCount(); i++) {
                if (Integer.parseInt(spDuracao.getItemAtPosition(i).toString()) == exercicioParaEditar.getDuracao()) {
                    duracaoPosition = i;
                    break;
                }
            }
            spDuracao.setSelection(duracaoPosition);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_limpar) {
            limparFormulario();
            return true;
        } else if (id == R.id.action_salvar) {
            salvarFormulario();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void limparFormulario() {
        etNome.setText("");
        rgTipo.clearCheck();
        cbConcluido.setChecked(false);
        spDuracao.setSelection(0);
        Toast.makeText(this, getString(R.string.informacoes_limpadas), Toast.LENGTH_SHORT).show();
    }

    private void salvarFormulario() {
        String nome = etNome.getText().toString();
        RadioButton rbTipo = findViewById(rgTipo.getCheckedRadioButtonId());
        String tipo = rbTipo != null ? rbTipo.getText().toString() : null;
        boolean concluido         = cbConcluido.isChecked();
        int duracao = Integer.parseInt(spDuracao.getSelectedItem().toString());
        if (nome.isEmpty() || tipo == null) {
            Toast.makeText(this, getString(R.string.preencha_todos_campos), Toast.LENGTH_SHORT).show();
            return;
        }

        Exercicio exercicio = new Exercicio(nome, tipo, concluido, duracao);
        Intent data = new Intent();
        data.putExtra("exercicio", exercicio);

        if (position != -1) {
            data.putExtra("position", position);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}

