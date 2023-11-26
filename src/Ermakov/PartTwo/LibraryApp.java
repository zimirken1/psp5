package Ermakov.PartTwo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class LibraryApp extends JFrame {
    private DefaultListModel<Book> bookListModel;
    private JList<Book> bookList;
    private JTextField titleField;
    private JTextField authorField;
    private JComboBox<String> genreComboBox;
    private JTextField yearField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton loadButton;

    public LibraryApp() {
        setTitle("Electronic Library");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
    }

    private void initializeComponents() {
        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);

        titleField = new JTextField(20);
        authorField = new JTextField(20);
        genreComboBox = new JComboBox<>(new String[]{"Fiction", "Non-Fiction", "Mystery", "Science Fiction", "Fantasy"});
        yearField = new JTextField(10);

        addButton = new JButton("Add Book");
        editButton = new JButton("Edit Book");
        deleteButton = new JButton("Delete Book");
        saveButton = new JButton("Save Library");
        loadButton = new JButton("Load Library");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Genre:"));
        inputPanel.add(genreComboBox);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(bookList), BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        add(mainPanel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBook();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLibrary();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadLibrary();
            }
        });
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = (String) genreComboBox.getSelectedItem();
        String yearText = yearField.getText();

        if (title.isEmpty() || author.isEmpty() || yearText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter title, author, and year.");
        } else {
            try {
                int year = Integer.parseInt(yearText);
                Book newBook = new Book(title, author, genre, year);
                bookListModel.addElement(newBook);

                titleField.setText("");
                authorField.setText("");
                yearField.setText("");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Year should be a valid number.");
            }
        }
    }

    private void editBook() {
        int selectedIndex = bookList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to edit.");
        } else {
            String newTitle = titleField.getText();
            String newAuthor = authorField.getText();

            if (newTitle.isEmpty() || newAuthor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both title and author.");
            } else {
                Book selectedBook = bookListModel.getElementAt(selectedIndex);
                selectedBook.setTitle(newTitle);
                selectedBook.setAuthor(newAuthor);
                bookListModel.setElementAt(selectedBook, selectedIndex);
                titleField.setText("");
                authorField.setText("");
                yearField.setText("");
            }
        }
    }

    private void deleteBook() {
        int selectedIndex = bookList.getSelectedIndex();
        if (selectedIndex != -1) {
            bookListModel.remove(selectedIndex);
        }
    }

    private void saveLibrary() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()))) {
                oos.writeObject(bookListModel);
                JOptionPane.showMessageDialog(this, "Library saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error while saving the library.");
                e.printStackTrace();
            }
        }
    }

    private void loadLibrary() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            bookListModel.clear();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()))) {
                DefaultListModel<Book> loadedListModel = (DefaultListModel<Book>) ois.readObject();
                for (int i = 0; i < loadedListModel.getSize(); i++) {
                    Book book = loadedListModel.getElementAt(i);
                    bookListModel.addElement(book);
                }
                JOptionPane.showMessageDialog(this, "Library loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error while loading the library.");
                e.printStackTrace();
            }
        }
    }
}

class Book implements Serializable {
    private String title;
    private String author;
    private String genre;
    private int year;

    public Book(String title, String author, String genre, int year) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + year + ")";
    }
    public void setAuthor(String newAuthor){
        this.author = newAuthor;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }
}
