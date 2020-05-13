package kb;

import parsing.types.Clause;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Dataloader {

    public BeliefBase loadData () throws IOException {

       // HashSet<Clause> clauses = new HashSet<Clause>();

        BeliefBase bb = new BeliefBase();

        // Data file to open.
        String fileName = "data/testclauses.txt";

        String line = null;

        try {
            FileReader fileReader = null;

            fileReader = new FileReader(fileName);


            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Remove duplicate lines
            HashSet<String> lines = new HashSet<String>();
            while ((line = bufferedReader.readLine()) != null) {
                if (line != null && !line.isEmpty()) {
                    lines.add(line);
                }
            }


            for (String statement : lines) {
                bb.tell(statement);
            }

            bufferedReader.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bb;
    }

}
