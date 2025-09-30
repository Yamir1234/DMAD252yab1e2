package mx.edu.tesoem.isc.DMAD252yab1e2;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ArchivoTXT {
    private final String NomArch = "Datos.txt";
    ArrayList<String> datos = new ArrayList<>();

    public boolean LeerArchivo(Context ctx) {
        try {
            InputStreamReader archivo = new InputStreamReader(ctx.openFileInput(NomArch));
            BufferedReader br = new BufferedReader(archivo);
            String linea;
            datos.clear();

            // SIEMPRE AGREGAR ENCABEZADOS PRIMERO
            datos.add("Nombre");
            datos.add("Edad");
            datos.add("Correo");

            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {

                    if (!linea.equals("Nombre") && !linea.equals("Edad") && !linea.equals("Correo")) {
                        datos.add(linea);
                    }
                }
            }
            br.close();
            archivo.close();
            return true;
        } catch (Exception ex) {

            datos.add("Nombre");
            datos.add("Edad");
            datos.add("Correo");
            return false;
        }
    }

    public boolean EscribeArchivo(Context ctx, ArrayList<String> Datos) {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(ctx.openFileOutput(NomArch, Context.MODE_PRIVATE));
            for (String Dato : Datos) {

                if (!Dato.equals("Nombre") && !Dato.equals("Edad") && !Dato.equals("Correo")) {
                    archivo.write(Dato + "\n");
                }
            }
            archivo.flush();
            archivo.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public ArrayList<String> getDatos() {
        return datos;
    }
}