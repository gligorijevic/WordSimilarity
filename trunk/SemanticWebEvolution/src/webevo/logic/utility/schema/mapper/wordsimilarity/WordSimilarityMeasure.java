/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webevo.logic.utility.schema.mapper.wordsimilarity;

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
        protected PathFinder pathFinder;
        protected DepthFinder depthFinder;
        
        public RelatednessCalculator( ILexicalDatabase db ) {
                this.db = db;
                pathFinder = new PathFinder(db);
                depthFinder = new DepthFinder(db);
        }
        
        public final static boolean useRootNode; 
        
        static {
                c = WS4JConfiguration.getInstance();
                enableCache = c.useCache();
                enableTrace = c.useTrace();
                useRootNode = true;
        }
        
        private WordSimilarityCalculator wordSimilarity = new WordSimilarityCalculator();

        // abstract hook method to be implemented
        protected abstract Relatedness calcRelatedness( Concept synset1, Concept synset2 );
                
        public abstract List<POS[]> getPOSPairs();
        
        // template method
        public Relatedness calcRelatednessOfSynset( Concept synset1, Concept synset2 ) {
                long t0 = System.currentTimeMillis();
                Relatedness r = calcRelatedness( synset1, synset2 );
                long t1 = System.currentTimeMillis();
                r.appendTrace( "Process done in = "+(double)(t1-t0)/1000D+" sec (cache: "+(Configuration.getInstance().useCache()?"enabled":"disabled")+").\n" );
                return r;
        }
        
        public double calcRelatednessOfWords( String word1, String word2 ) {
                return wordSimilarity.calcRelatednessOfWords(word1, word2, this);
        }
        
        public double[][] getSimilarityMatrix( String[] words1, String[] words2 ) {
                return MatrixCalculator.getSimilarityMatrix(words1, words2, this);
        }
        
        public double[][] getNormalizedSimilarityMatrix( String[] words1, String[] words2 ) {
                return MatrixCalculator.getNormalizedSimilarityMatrix(words1, words2, this);
        }
        
        /**
         * @return the db
         */
        public ILexicalDatabase getDB() {
                return db;
        }
             
}
