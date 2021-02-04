package com.example.handlingformsubmission.controller;

import com.example.handlingformsubmission.dto.Entry;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

@Controller
public class TableController {

    @Value("${csv.path}")
    private String fileName;

    @GetMapping("/table")
    public String getTable(Model model) {

        try (Reader reader = new FileReader(fileName))
        {
            CsvToBean<Entry> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Entry.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Entry> entries = csvToBean.parse();

            model.addAttribute("entries", entries);

        } catch (Exception ex) {
            model.addAttribute("message", "An error occurred while processing the CSV file.");
        }
        return "table";
    }

}
