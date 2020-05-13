package kb;

import parsing.types.Clause;
import parsing.types.Literal;

import java.util.*;
import java.util.stream.Collectors;

public class kbController {

    public  boolean plResolution(Clause inputClauses, Clause question){
        HashSet<Clause> clauses = new HashSet();
        clauses.add(inputClauses);
        return  plResolution(clauses, question);
    }

    public boolean plResolution(HashSet<Clause> inputClauses, Clause question) {
        //Ini clauses
        Set<Clause> clauses = new HashSet<>();
        clauses.addAll(inputClauses);
        Set<Clause> negatedQuestion = negate(question);
        clauses.addAll(negatedQuestion);

        //Ini new clauses
        HashSet<Clause> newClauses = new HashSet<>();

        while (true) {
            //for each pair of clauses in set 'Clauses'
            for (Clause c1 : clauses) {
                for (Clause c2 : clauses) {
                    if(!c1.equals(c2)) {
                        Set<Clause> resolvedClause = plResolve(c1, c2);
                        // List<Clause> resolvedList = new ArrayList<>(resolvedClause);
                        if (resolvedClause.contains(Clause.emptyClause))
                            return true;
                        newClauses.addAll(resolvedClause);
                    }
                }
            }
            if (clauses.containsAll(newClauses))
                return false;

            clauses.addAll(newClauses);
        }
    }

    public Set<Clause> plResolve(Clause c1, Clause c2) {

        Set<Clause> resolvedClauses = new HashSet<>();

        int complementaryLiterals = 0;

        Set<Literal> complLiterals = new HashSet<>();

        for (Literal l1 : c1.getLiterals()) {
            for (Literal l2 : c2.getLiterals()) {
                if(l1.literal.equals(l2.literal) && l1.isNot != l2.isNot) {
                    complementaryLiterals += 1;
                    complLiterals.add(l1);
                    complLiterals.add(l2);
                }
            }
        }

        if(complementaryLiterals == 1) {
            Set<Literal> allLiterals = new HashSet<>();
            allLiterals.addAll(c1.getLiterals());
            allLiterals.addAll(c2.getLiterals());

            for(Literal l : complLiterals) {
                if (allLiterals.contains(l))
                    allLiterals.remove(l);
            }

            Clause newClause = new Clause();

            newClause.setLiterals(allLiterals);

            resolvedClauses.add(newClause);

            return resolvedClauses;
        }
        else {
            return resolvedClauses;
        }
    }

    private Set<Clause> negate(Clause c) {
        Set<Clause> negatedClauses = new HashSet<>();

        for (Literal l : c.getLiterals()) {
            //l.setLiteral(!l.isNot);
            Clause negatedClause = new Clause();
            negatedClause.setLiteral(new Literal(!l.isNot, l.literal));
            negatedClauses.add(negatedClause);
        }
        //return c;
        return negatedClauses;
    }

            /*
1. Belief base definereres som BB.
2. Du vil gerne fjerne, f.eks. q fra BB.
3. Tjek hver clause i BB' sæt af clauses, og se om hver clause entailer q.

4. Hvis det enkelte clause entailer q, fjern denne clause fra sættet.

5. Hvis clausen IKKE entailer q, behold clausen (og evt. læg det i et nyt sæt kaldt 'remainders')
6. Udregn rangen på hver af remainder clauses og behold de 2-3 højest rangeret?
7. De højest rangeret clauses er nu det nye sæt for BB.
    *  */

    public void contractionBB(BeliefBase bb, Clause c) {
            HashSet<Clause> remainders = new HashSet();

            for (Clause c1 : bb.getClauses()) {
                if(!plResolution(c1, c)) {
                    remainders.add(c1);
                }
            }
            /*

            remainderset = arraylist()

listofclauses = arraylist(clauses)
while(!listofclauses.empty){
    clauseset = arraylist()
    counter = 0
    foreach (clause in listofclauses){
        clauseset.add(clause)
        if(entails(clauseset, something)){
            remainderset.add(clauseset)
            break;
        }
        counter++;
    }
    listofclauses.removerange(counter)
}
             */

            ArrayList<Clause> checkList = (ArrayList<Clause>) remainders.stream().collect(Collectors.toList());
            remainders.clear();

            HashSet<List<Clause>> tempHash = new HashSet<>();

            List<Clause> remainderList = new ArrayList();
            Iterator i = checkList.listIterator();

            while (i.hasNext()) {

                if(plResolution(remainders, c)){
                    tempHash.add(remainderList);
                    remainderList.clear();
                }else {
                    remainderList.add((Clause) i.next());
                }
            }


            rank


    }

    private int rankBB(BeliefBase bb) {
        int sum = 0;
        for (Clause x: bb.getClauses()) {
            sum = sum + x.getLiterals().size();
        }
        return sum;
    }



    private int rank(Clause c) {
        return c.getLiterals().size();
    }

    public void expansionBB(BeliefBase bb, Clause c) {
        if(!bb.getClauses().contains(c))
            bb.getClauses().add(c);
    }

}
