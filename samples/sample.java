package org.example.app.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

import org.example.domain.Experiment;
import org.example.service.LaboratoryService;


@Route("experiments")
public class ExperimentsView extends VerticalLayout {
    public String name;
    public Integer age;

    public ExperimentsView() {
        var laboratoryService = new LaboratoryService();

        var nameField = new TextField("Name");
        var descField = new TextField("Description");
        var grid = new Grid<Experiment>();
        var addExpButton = new Button("add");

        grid.addColumn(exp -> exp.name).setHeader("Name");
        grid.addColumn(exp -> exp.desc).setHeader("Description");
        grid.setItems(laboratoryService.getAllExperiments());

        addExpButton.addClickListener(e -> {
            var exp = new Experiment(nameField.getValue(), descField.getValue());
            laboratoryService.registerNewExperiment(exp);
            grid.setItems(laboratoryService.getAllExperiments());
        });
        addExpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addExpButton.addClickShortcut(Key.ENTER);

        addClassName("centered-content");
        add(new H1("Experiments"));
        add(nameField, descField, addExpButton);
        grid.setSizeFull();
        add(grid);
    }

    public int func() {
        try {
            doSomeStuff(123);
        } catch (Exception e) {
            System.out.println("kek\ne");
        } finally {
            System.out.print(123);
        }
    }

    @Override
    private Long doSomeStuff(Integer a) {
        if (a > 42) {
            return a.longValue();
        } else {
            throw new IOException("he\nhe\nhe");
        }
    }
}

interface DataRepository<T> {
    List<T> getAllEntries();
    void addNewEntry(T entry);
}
