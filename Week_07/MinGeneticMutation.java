import java.util.HashSet;
import java.util.Set;
//433. 最小基因变化
public class MinGeneticMutation {
    public int minMutation(String start, String end, String[] bank) {
        if (start == null || end == null || bank == null) return -1;
        if (start.equals(end)) return 0;

        Set<String> geneSet = new HashSet<>();
        for (String gene : bank) 
            geneSet.add(gene);
        
        if (!geneSet.contains(end)) return -1;

        Set<String> startSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        Set<String> visited = new HashSet<>();
        int len = start.length();
        int step = 0;
        startSet.add(start);
        endSet.add(end);
        char[] geneArr = new char[]{'A', 'C', 'G', 'T'};

        while (!startSet.isEmpty() && !endSet.isEmpty()) {
            if (startSet.size() > endSet.size()) {
                Set<String> tmp = startSet;
                startSet = endSet;
                endSet = tmp;
            }
            Set<String> currLayer = new HashSet<>();
            for (String gene : startSet) {
                char[] currGene = gene.toCharArray();
                for (int i = 0; i < len; i++) {
                    char origin = currGene[i];
                    for (char g : geneArr) {
                        currGene[i] = g;
                        String tmpGene = String.valueOf(currGene);
                        if (endSet.contains(tmpGene)) return step + 1;
                        if (!visited.contains(tmpGene) && geneSet.contains(tmpGene)) {
                            currLayer.add(tmpGene);
                            visited.add(tmpGene);
                        }
                    }
                    currGene[i] = origin;
                }
            }
            startSet = currLayer;
            step++;
        }
        return -1;
    }

    public static void main(String[] args) {
        MinGeneticMutation mutation = new MinGeneticMutation();
        String start = "AAAAAAAA";
        String end = "CCCCCCCC";
        String[] bank = new String[] {"AAAAAAAA","AAAAAAAC","AAAAAACC","AAAAACCC","AAAACCCC","AACACCCC","ACCACCCC","ACCCCCCC","CCCCCCCA"};
        System.out.println(mutation.minMutation(start, end, bank));
    }
}
