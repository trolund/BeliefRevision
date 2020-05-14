package kb;

import parsing.types.Clause;
import parsing.types.Literal;

import java.util.*;
import java.util.stream.Collectors;

public class kbController {

    public boolean plResolution(List<Clause> inputClauses, Clause question) {
        Set<Clause> clauses = new HashSet<>(inputClauses);
        return plResolution(clauses, question);
    }

    public  boolean plResolution(Clause inputClauses, Clause question){
        HashSet<Clause> clauses = new HashSet();
        clauses.add(inputClauses);
        return  plResolution(clauses, question);
    }

    public boolean plResolution(Set<Clause> inputClauses, Clause question) {
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

        // Belief base: {p, q, p or q, not p or q} c = {q}
        //{p, p or q, not p or q}

        ArrayList<Clause> checkList = (ArrayList<Clause>) remainders.stream().collect(Collectors.toList()); //Liste af remainders {c1, c2, c3, c4....} hvor cn = vilkårlig clause
        remainders.clear();

        Set<List<Clause>> tempHash = new HashSet<>(); //[{c1,c2,c3}, {c4, c6}]

        List<Clause> remainderList = new ArrayList();

        /*
        while (checkList.size() != 0) {

            Clause initialClause = checkList.get(0); //p
            remainderList.add(initialClause);

            for(int i = 1; i < checkList.size(); i++) {
                Clause clause = checkList.get(i);
                remainderList.add(clause); //p + p or q + not p or q

                if(plResolution(remainderList, c)) {
                    remainderList.remove(clause); //p + p or q
                }
            }

            checkList.removeAll(remainderList); //p + p or q  ---> not p or q
            List<Clause> tempList = new ArrayList<>(remainderList);
            tempHash.add(tempList); //Liste1 : p + p or q, Liste2: not p or q
            remainderList.clear();
        }
         */

        //Prøver at implementere således algoritmen tager stilling til ALLE kombinationer
        while (checkList.size() != 0) {

            //Clause initialClause = checkList.get(0); //p
            //remainderList.add(initialClause);

            int size = 1;
            int maxSize = 1;

            for (int i = 0; i < checkList.size(); i++) {
                Clause firstClause = checkList.get(i); // {ci}
                remainderList.add(firstClause);

                for (int j = 0; j < checkList.size(); j++) {
                    Clause clause = checkList.get(j);
                    if(!firstClause.equals(clause) && !remainderList.contains(clause)) { //Don't add duplicates
                        remainderList.add(clause);

                        if (plResolution(remainderList, c)) {
                            remainderList.remove(clause);
                            size--;
                        }
                        size++;
                    }
                }

                if(size > maxSize) {
                    maxSize = size;
                    List<Clause> tempList = new ArrayList<>(remainderList);
                    remainderList.clear();
                    //tempHash.clear();
                    tempHash.add(tempList);
                }
                size = 0;
            }

            int listSize = 0;
            for(List l : tempHash) {
                if(l.size() > listSize)
                    listSize = l.size();
            }

            Iterator iterator = tempHash.iterator();

            List<Clause> largestRemainder = new ArrayList<>();

            while(iterator.hasNext()) {
                List<Clause> next = (List<Clause>) iterator.next();
                if(next.size() == listSize)
                    largestRemainder.addAll(next);

            }


            checkList.removeAll(largestRemainder); //p + p or q  ---> not p or q
            //List<Clause> tempList = new ArrayList<>(remainderList);
            //tempHash.add(tempList); //Liste1 : p + p or q, Liste2: not p or q
            //remainderList.clear();
        }

        System.out.println("Remainder sets:");
        for(List l : tempHash) {
            System.out.println(l.toString());
        }

        //Selection function

        //Tilføj 'bedste' remainder list til belief base

        bb.getClauses().clear(); //Clearer belief base
        for(List l : tempHash) { //Looper og tempHash som indeholder sæt af lister af clauses
            bb.getClauses().addAll(l); //tilføjer alle lister af clauses til belief base
        }

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
