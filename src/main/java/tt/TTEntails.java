package tt;

import kb.BeliefBase;
import parsing.types.Literal;
import parsing.types.Node;

import java.util.ArrayList;
import java.util.List;

public class TTEntails {


    //{p, q, !z}, {p v z}
    public boolean entails(BeliefBase kb, Node node) {

        List<Literal> literals = new ArrayList<Literal>();

        //TODO add literals til liste
        literals.addAll(kb.getLiterals());
        literals.addAll(parseQuestion(node));





        //Resolution




        //if clausen er tom, return true

        //else return false

    }

    private List<Literal> parseQuestion(Node node) {
        if(node.getChildren().size() == 0) {
            //literals.add(((Literal) node.getData()));
            List<Literal> shortlist = new ArrayList<Literal>();
            shortlist.add((Literal) node.getData());
            return shortlist;
        }
        else {
            List<Literal> literals = new ArrayList<Literal>();

            for (Object childnode : node.getChildren()) {
                literals.addAll(parseQuestion((Node) childnode));
            }

            return literals;
        }
    }
}