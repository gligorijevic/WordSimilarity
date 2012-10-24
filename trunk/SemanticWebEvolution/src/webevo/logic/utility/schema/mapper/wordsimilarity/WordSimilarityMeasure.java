/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webevo.logic.utility.schema.mapper.wordsimilarity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import webevo.domain.mapper.Word;
import webevo.logic.utility.schema.mapper.tools.CollectionsTool;

/**
 *
 * @author Djordje
 */
public abstract class WordSimilarityMeasure {

    private final static WS4JConfiguration c;
    public final static boolean enableCache;
    public final static boolean enableTrace;
    protected final static String illegalSynset = "Synset is null.";
    protected final static String identicalSynset = "Synsets are identical.";
    protected ILexicalDatabase db;
    //protected PathFinder pathFinder;
    protected DepthFinder depthFinder;

    public RelatednessCalculator(ILexicalDatabase db) {
        this.db = db;
        pathFinder = new PathFinder(db);
        depthFinder = new DepthFinder(db);
    }
    public final static boolean useRootNode;

//    static {
//        c = WS4JConfiguration.getInstance();
//        enableCache = c.useCache();
//        enableTrace = c.useTrace();
//        useRootNode = true;
//    }
    private WordSimilarityCalculator wordSimilarity = new WordSimilarityCalculator();

    // abstract hook method to be implemented
    protected abstract double calcRelatedness(Word synset1, Word synset2);

    public abstract List<char[]> getWordTypes();

    // template method
    public double calcRelatednessOfSynset(Word synset1, Word synset2) {
        long t0 = System.currentTimeMillis();
        double r = calcRelatedness(synset1, synset2);
        long t1 = System.currentTimeMillis();
//        r.appendTrace("Process done in = " + (double) (t1 - t0) / 1000D + " sec (cache: " + (Configuration.getInstance().useCache() ? "enabled" : "disabled") + ").\n");
        return r;
    }

    public double calcRelatednessOfWords(String word1, String word2) {
        return wordSimilarity.calcRelatednessOfWords(word1, word2, this);
    }

    public double[][] getSimilarityMatrix(String[] words1, String[] words2) {
        return MatrixCalculator.getSimilarityMatrix(words1, words2, this);
    }

    public double[][] getNormalizedSimilarityMatrix(String[] words1, String[] words2) {
        return MatrixCalculator.getNormalizedSimilarityMatrix(words1, words2, this);
    }

    /**
     * @return the db
     */
    public ILexicalDatabase getDB() {
        return db;
    }

    /*----------------------PATH FINDER------------------------*/
    /**
     *
     * Used by DepthFinder, ICFinder, and PathFinder
     *
     * @param synset1
     * @param synset2
     * @param pos
     * @param mode
     * @return A list of all paths, sorted by path length in ascending order.
     * The format for each item in the list is a reference to an array that has
     * the format: [$top, $length, [
     * @synsets_list]] where
     * @synset_list is a list of synsets along the path (including the two input
     * synsets) (from wikipedia::similarity)
     */
    public List<Subsumer> getAllPaths(Word synset1, Word synset2,
            StringBuilder tracer) {

        List<Subsumer> paths = new ArrayList<>();

        // This is where db access occurs.

        Set<String> history = new HashSet<>();
        List<List<String>> lTrees = getHypernymTrees(synset1.getSynset(), history);

        history = new HashSet<>();
        List<List<String>> rTrees = getHypernymTrees(synset2.getSynset(), history);

        if (lTrees == null || rTrees == null) {
            return null;
        }

        for (List<String> lTree : lTrees) {
            for (List<String> rTree : rTrees) {
                String subsumer = getSubsumerFromTrees(lTree, rTree);

                if (subsumer == null) {
                    continue;
                }

                int lCount = 0;
                List<String> lpath = new ArrayList<>(lTree.size());
                List<String> reversedLTree = CollectionsTool.reverse(lTree);

                for (String synset : reversedLTree) {
                    lCount++;
                    if (synset.equals(subsumer)) {
                        break;
                    }
                    lpath.add(synset);
                }

                int rCount = 0;
                List<String> rpath = new ArrayList<>(rTree.size());
                List<String> reversedRTree = CollectionsTool.reverse(rTree);
                for (String synset : reversedRTree) {
                    rCount++;
                    if (synset.equals(subsumer)) {
                        break;
                    }
                    rpath.add(synset);
                }

                Subsumer sub = new Subsumer();
                sub.subsumer = new Word(subsumer, synset1.getWordType(), null, null);
                sub.length = rCount + lCount - 1;
                sub.lpath = lpath;
                sub.rpath = rpath;
                paths.add(sub);

                if (tracer != null) {
                    tracer.append("HyperTree1:");
                    for (String synset : lTree) {
                        tracer.append(" "
                                + db.conceptToString(synset));
                    }
                    tracer.append("\n");
                    tracer.append("HyperTree2:");
                    for (String synset : rTree) {
                        tracer.append(" "
                                + db.conceptToString(synset));
                    }
                    tracer.append("\n");
                }
            }
        }

        // asc sort by length
        Collections.sort(paths, new Comparator<Subsumer>() {
            @Override
            public int compare(Subsumer s1, Subsumer s2) {
                if (s1.length > s2.length) {
                    return 1;
                } else if (s1.length < s2.length) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        return paths;
    }

    public List<Subsumer> getShortestPaths(Word synset1,
            Word synset2, StringBuilder tracer) {
        List<Subsumer> returnList = new ArrayList<>();
        List<Subsumer> paths = getAllPaths(synset1, synset2, tracer);
        if (paths == null || paths.isEmpty()) {
            return returnList;
        }
        int bestLength = paths.get(0).length;

        returnList.add(paths.get(0));
        for (int i = 1; i < paths.size(); i++) {
            if (paths.get(i).length > bestLength) {
                break;
            }
            returnList.add(paths.get(i));
        }
        return returnList;
    }

    /**
     *
     * @param list1
     * @param list2
     * @return synset of the subsumer
     */
    private static String getSubsumerFromTrees(List<String> list1,
            List<String> list2) {
        List<String> tree1 = CollectionsTool.reverse(list1);
        List<String> tree2 = CollectionsTool.reverse(list2);

        String tree1Joined = " " + CollectionsTool.join(" ", tree1) + " ";
        for (String synset2 : tree2) {
            if (tree1Joined.indexOf(synset2) != -1) {
                return synset2;
            }
        }

        return null;
    }

   
    
    /**
     * since this method is heavily used, inner cache would help for e.g.
     * calculating similarity matrix
     *
     * Suroutine that returns an array of hypernym trees, given the offset of #
     * the synset. Each hypernym tree is an array of offsets.
     *
     * @param synset
     * @param mode
     */
    public List<List<String>> getHypernymTrees(String synset, Set<String> history) {
//        WS4JConfiguration.getInstance().setCache(false);
//        String key = synset;
//
//        if (WS4JConfiguration.getInstance().useCache()) {
//            List<List<String>> cachedObj = cache.get(key);
//            if (cachedObj != null) {
//                return clone(cachedObj);
//            }
//        }
        // check if the input synset is one of the imaginary root nodes
        if (synset.equals("0")) {
            List<String> tree = new ArrayList<>();
            tree.add("0");
            List<List<String>> trees = new ArrayList<List<String>>();
            trees.add(tree);
//            if (WS4JConfiguration.getInstance().useCache()) {
////				synchronized ( cache ) {
//                if (cache.size() >= WS4JConfiguration.getInstance().getMaxCacheSize()) {
//                    cache.remove(cache.keySet().iterator().next());
//                }
//                if (trees != null) {
//                    cache.put(key, clone(trees));
//                }
//            }
//			}
            return trees;
        }

        List<String> synlinks = (List<String>) db.getHypernyms(synset);

        List<List<String>> returnList = new ArrayList<>();
        if (synlinks.isEmpty()) {
            List<String> tree = new ArrayList<>();
            tree.add(synset);
            tree.add(0, "0");
            returnList.add(tree);
        } else {
            for (String hypernym : synlinks) {
                if (history.contains(hypernym)) {
                    continue;
                }
                history.add(hypernym);

                List<List<String>> hypernymTrees = getHypernymTrees(hypernym, history);
                if (hypernymTrees != null) {
                    for (List<String> hypernymTree : hypernymTrees) {
                        hypernymTree.add(synset);
                        returnList.add(hypernymTree);
                    }
                }
                if (returnList.isEmpty()) {
                    List<String> newList = new ArrayList<>();
                    newList.add(synset);
                    newList.add(0, "0");
                    returnList.add(newList);
                }
            }
        }

//        if (WS4JConfiguration.getInstance().useCache()) {
////			synchronized ( cache ) {
//            if (cache.size() >= WS4JConfiguration.getInstance().getMaxCacheSize()) {
//                cache.remove(cache.keySet().iterator().next());
//            }
//            if (returnList != null) {
//                cache.put(key, clone(returnList));
//            }
//        }
//		}
        return returnList;
    }

    // TODO: refactor names!!
    public static class Subsumer {

        public Word subsumer;
        public int length;
        public double ic;
        public List<String> lpath;
        public List<String> rpath;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{ ");
            sb.append("\"subsumer\" : \"").append(subsumer).append("\", ");
            sb.append("\"length\" : \"").append(length).append("\", ");
            sb.append("\"ic\" : \"").append(ic).append("\", ");
            sb.append("\"lpath\" : \"").append(lpath).append("\", ");
            sb.append("\"rpath\" : \"").append(rpath).append("\"");
            sb.append(" }");
            return sb.toString();
        }
    }

    // return non-zero root
    public Word getRoot(String synset) {
        Set<String> history = new HashSet<>();
        List<List<String>> paths = getHypernymTrees(synset, history);
        if (paths != null && paths.size() > 0 && paths.get(0).size() > 1) {
            return new Word(paths.get(0).get(1));
        } else if (paths != null && paths.size() > 0) {
            // TODO: is this ok? ad hoc impl to avoid IndexOutOfBoundsException
            // for [[0]]
            return new Word(paths.get(0).get(0));
        }
        return null;
    }

    public List<Subsumer> getLCSByPath(Word synset1, Word synset2,
            StringBuilder tracer) {
        List<Subsumer> paths = getAllPaths(synset1, synset2, tracer);
        List<Subsumer> returnList = new ArrayList<>(paths.size());
        if (paths == null) {
            return returnList;
        }

        for (Subsumer path : paths) {
            if (path.length <= paths.get(0).length) {
                returnList.add(path);
            }
        }

        return returnList;
    }

    private static List<List<String>> clone(List<List<String>> original) {
        List<List<String>> clone = new ArrayList<>(original.size());
        for (List<String> oStrings : original) {
            List<String> cStrings = new ArrayList<>(oStrings.size());
            for (String oString : oStrings) {
                cStrings.add(oString);
            }
            clone.add(cStrings);
        }
        return clone;
    }
    /*----------------------END OF PATH FINDER------------------------*/
}
