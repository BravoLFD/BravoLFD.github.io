class Contador {
    private int valor = 0; //Es el contador compartido

    // Método sincronizado: incrementa y devuelve el valor actualizado
    public synchronized int incrementarYObtener() {
        valor++;
        return valor;
    }

    // Método para obtener valor final sin modificarlo
    public synchronized int getValor() {
        return valor;
    }
}

class TareaIncremento implements Runnable {
    private Contador contador;
    private String nombreHilo;

    public TareaIncremento(Contador contador, String nombreHilo) {
        this.contador = contador; //recurso compartido
        this.nombreHilo = nombreHilo; // Nombre del hilo
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {

            // cada hilo incrementa el contador 10 veces
            int nuevoValor = contador.incrementarYObtener();

            // Imprimir siempre el valor correcto
            System.out.println("Hilo " + nombreHilo +
                               " incrementa contador a: " + nuevoValor); //Incremento

            // Pausa para visualizar intercalación de hilos
            try {
                Thread.sleep((int)(Math.random() * 300) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Hilo " + nombreHilo + " termino."); //mensaje para indicar que el hilo ha terminado
    }
}

public class ContadorConcurrente {
    public static void main(String[] args) {
        Contador contador = new Contador(); // Recurso compartido
        //Se crean tres hilos que incrementan el contador
        Thread h1 = new Thread(new TareaIncremento(contador, "H1"));
        Thread h2 = new Thread(new TareaIncremento(contador, "H2"));
        Thread h3 = new Thread(new TareaIncremento(contador, "H3"));
        // Inician los hilos
        h1.start();
        h2.start();
        h3.start();
        // Esperar a que terminen los hilos
        try {
            h1.join();
            h2.join();
            h3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Valor Final del contador
        System.out.println("\nValor final del contador: " + contador.getValor());
    }
}