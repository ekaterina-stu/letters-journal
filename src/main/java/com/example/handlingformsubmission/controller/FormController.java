package com.example.handlingformsubmission.controller;

import com.example.handlingformsubmission.dto.Entry;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class FormController {

    @Value("${csv.path}")
    private String fileName;

    private static final String CSV_SEPARATOR = ",";

    @Value("${csv.num}")
    private String INITIAL_NUM;

    @GetMapping("/")
    public String redirect(Model model) {
        return "redirect:/form";
    }

    @GetMapping("/form")
    public String entryForm(Model model, Principal principal) {
        Entry entry = new Entry();
        entry.setNumber(makeNumber());
        entry.setUser(principal.getName());
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("entry", entry);
        return "form";
    }

    @PostMapping("/form")
    public String entrySubmit(@ModelAttribute @Valid Entry entry, BindingResult bindingResult, Model model, Principal principal) {
        entry.setNumber(makeNumber());
        model.addAttribute("entry", entry);

        if (bindingResult.hasErrors()) {
            return "form";
        } else {

            try {

                FileWriter writer = new FileWriter(fileName, true);
                StringBuffer oneLine = new StringBuffer();
                writer.write(System.lineSeparator());
                oneLine.append(entry.getNumber());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(entry.getDate());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(entry.getOrganization());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(entry.getTheme());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(entry.getExecutor());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(principal.getName());

                writer.write(oneLine.toString());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return "redirect:/table";
        }
    }

    private String makeNumber() {
        try (Reader reader = new FileReader(fileName)) {
            CsvToBean<Entry> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Entry.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<Entry> entries = csvToBean.parse();
            String newNum;
            if (entries.size()>0) {
                String oldNum = entries.get(entries.size() - 1).getNumber();
                newNum = oldNum.substring(0, oldNum.indexOf('/') + 1) + (Integer.parseInt(oldNum.substring(oldNum.indexOf('/') + 1)) + 1);
            } else {
                newNum = INITIAL_NUM;
            }
            return newNum;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

