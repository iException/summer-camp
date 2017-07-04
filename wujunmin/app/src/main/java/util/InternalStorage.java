package util;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import entities.Note;

/* an utility class that perform I/O operations on device's local storage */
public class InternalStorage {
    private Context context;

    public InternalStorage(Context context) {
        this.context = context;
    }

    public void set(String filename, List<Note> content) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE));
        objectOutputStream.writeObject(content);
        objectOutputStream.close();
    }

    public List<Note> get(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(filename));
        List<Note> notes = (List<Note>) objectInputStream.readObject();
        objectInputStream.close();
        return notes;
    }
}