package mx.edu.tesoem.isc.DMAD252yab1e2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {

    ArrayList<String> datos = new ArrayList<>();
    EditText txtnombre, txtedad, txtcorreo;
    GridView gvdatos;
    ArrayAdapter<String> adaptador;
    Button btnNuevo, btnEditar, btnEliminar, btnGrabar1, btnGrabar2;

    private int posicionSeleccionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        txtnombre = findViewById(R.id.txtnombre);
        txtedad = findViewById(R.id.txtedad);
        txtcorreo = findViewById(R.id.txtcorreo);
        gvdatos = findViewById(R.id.gvdatos);
        btnNuevo = findViewById(R.id.btnp1nuevo);
        btnEditar = findViewById(R.id.btnp1editar);
        btnEliminar = findViewById(R.id.btnp1eliminar);
        btnGrabar1 = findViewById(R.id.btnGrabar1);
        btnGrabar2 = findViewById(R.id.btnGrabar2);

        habilitarEdicion(false);
        ocultarBotonesGrabar();


        cargarinfo();


        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
        gvdatos.setAdapter(adaptador);


        gvdatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 3 && (position % 3) == 0) {

                    posicionSeleccionada = position;


                    txtnombre.setText(datos.get(position));
                    txtedad.setText(datos.get(position + 1));
                    txtcorreo.setText(datos.get(position + 2));


                    habilitarEdicion(false);
                    ocultarBotonesGrabar();

                    Toast.makeText(PrincipalActivity.this, "Registro seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modoNuevo();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modoEditar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarRegistro();
            }
        });

        btnGrabar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarNuevo();
            }
        });

        btnGrabar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarEdicion();
            }
        });
    }

    private void modoNuevo() {
        limpiarCampos();
        habilitarEdicion(true);
        mostrarBotonGrabar1();
        posicionSeleccionada = -1;
    }

    private void modoEditar() {
        if (posicionSeleccionada == -1) {
            Toast.makeText(this, "Seleccione un registro primero", Toast.LENGTH_SHORT).show();
            return;
        }
        habilitarEdicion(true);
        mostrarBotonGrabar2();
    }

    private void grabarNuevo() {
        String nombre = txtnombre.getText().toString().trim();
        String edad = txtedad.getText().toString().trim();
        String correo = txtcorreo.getText().toString().trim();

        if (nombre.isEmpty() || edad.isEmpty() || correo.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }


        if (existeNombre(nombre)) {
            Toast.makeText(this, "El nombre ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        if (existeCorreo(correo)) {
            Toast.makeText(this, "El correo ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        datos.add(nombre);
        datos.add(edad);
        datos.add(correo);

        if (guardarDatos()) {
            Toast.makeText(this, "Nuevo registro guardado", Toast.LENGTH_SHORT).show();
            finalizarGrabacion();
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private void grabarEdicion() {
        if (posicionSeleccionada == -1) {
            Toast.makeText(this, "No hay registro seleccionado", Toast.LENGTH_SHORT).show();
            return;
        }

        String nombre = txtnombre.getText().toString().trim();
        String edad = txtedad.getText().toString().trim();
        String correo = txtcorreo.getText().toString().trim();

        if (nombre.isEmpty() || edad.isEmpty() || correo.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }


        String nombreOriginal = datos.get(posicionSeleccionada);
        String correoOriginal = datos.get(posicionSeleccionada + 2);

        if (!nombre.equals(nombreOriginal) && existeNombre(nombre)) {
            Toast.makeText(this, "El nombre ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!correo.equals(correoOriginal) && existeCorreo(correo)) {
            Toast.makeText(this, "El correo ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar registro existente
        datos.set(posicionSeleccionada, nombre);
        datos.set(posicionSeleccionada + 1, edad);
        datos.set(posicionSeleccionada + 2, correo);

        if (guardarDatos()) {
            Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show();
            finalizarGrabacion();
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean existeNombre(String nombre) {
        for (int i = 0; i < datos.size(); i += 3) {
            if (i < datos.size() && datos.get(i).equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    private boolean existeCorreo(String correo) {
        for (int i = 2; i < datos.size(); i += 3) {
            if (i < datos.size() && datos.get(i).equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
    }

    private void eliminarRegistro() {
        if (posicionSeleccionada == -1) {
            Toast.makeText(this, "Seleccione un registro primero", Toast.LENGTH_SHORT).show();
            return;
        }


        datos.remove(posicionSeleccionada + 2);
        datos.remove(posicionSeleccionada + 1);
        datos.remove(posicionSeleccionada);

        if (guardarDatos()) {
            Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            posicionSeleccionada = -1;
            adaptador.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private void finalizarGrabacion() {
        habilitarEdicion(false);
        ocultarBotonesGrabar();
        limpiarCampos();
        posicionSeleccionada = -1;
        adaptador.notifyDataSetChanged();
    }

    private void habilitarEdicion(boolean habilitar) {
        txtnombre.setEnabled(habilitar);
        txtedad.setEnabled(habilitar);
        txtcorreo.setEnabled(habilitar);
    }

    private void limpiarCampos() {
        txtnombre.setText("");
        txtedad.setText("");
        txtcorreo.setText("");
    }

    private void mostrarBotonGrabar1() {
        btnGrabar1.setVisibility(View.VISIBLE);
        btnGrabar2.setVisibility(View.GONE);
    }

    private void mostrarBotonGrabar2() {
        btnGrabar1.setVisibility(View.GONE);
        btnGrabar2.setVisibility(View.VISIBLE);
    }

    private void ocultarBotonesGrabar() {
        btnGrabar1.setVisibility(View.GONE);
        btnGrabar2.setVisibility(View.GONE);
    }

    private boolean guardarDatos() {
        ArchivoTXT archivo = new ArchivoTXT();
        return archivo.EscribeArchivo(this, datos);
    }

    public void cargarinfo() {
        ArchivoTXT info = new ArchivoTXT();
        if (info.LeerArchivo(this)) {
            if (info.getDatos().size() > 0) {
                datos = info.getDatos();
                if (adaptador != null) {
                    adaptador.clear();
                    adaptador.addAll(datos);
                    adaptador.notifyDataSetChanged();
                }
            }
        }
    }
}