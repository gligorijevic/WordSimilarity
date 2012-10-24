/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webevo.logic.utility.schema.mapper.wordsimilarity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Djordje
 */
public class LeakokChodorow {
    
        protected static double min = 0; // or -Double.MAX_VALUE ?
        protected static double max = Double.MAX_VALUE;

        @SuppressWarnings("serial")
        private static List<POS[]> posPairs = new ArrayList<POS[]>(){{
                add(new POS[]{POS.n,POS.n});
                add(new POS[]{POS.v,POS.v});
        }};
        
        public LeacockChodorow(ILexicalDatabase db) {
                super(db);
                // TODO Auto-generated constructor stub
        }

        @Override
        protected Relatedness calcRelatedness( Concept synset1, Concept synset2 ) {
                StringBuilder tracer = new StringBuilder();
                if ( synset1 == null || synset2 == null ) return new Relatedness( min, null, illegalSynset );
                //Don't short-circuit!
                //if ( synset1.getSynset().equals( synset2.getSynset() ) ) return new Relatedness( max );
                
                StringBuilder subTracer = enableTrace ? new StringBuilder() : null;
                List<Subsumer> lcsList = pathFinder.getLCSByPath( synset1, synset2, subTracer );
                if ( lcsList.size() == 0 ) return new Relatedness( min );
                
                //TODO: investigate if these values are always valid for wn-jpn-0.9.0
                int maxDepth = 1;
                if ( synset1.getPos().equals( POS.n ) ) {
                        maxDepth = 20;
                } else if ( synset1.getPos().equals( POS.v ) ) {
                        maxDepth = 14;
                }
                
                //System.out.println(lcsList);
                int length = lcsList.get( 0 ).length;
                
//              int maxDepth = -1;      
//              for ( Depth lcs : lcsList ) {
//                      
//                      List<String> roots = getTaxonomies( lcs );
//                      for ( String root : roots ) {
//                              int depth = getTaxonomyDepth( root );
//                              if ( depth > maxDepth ) maxDepth = depth;
//                      }
//                      
//              }
                
                double score = -Math.log( (double)length / (double)( 2 * maxDepth ) );
                
                if ( enableTrace ) {
                        tracer.append( subTracer.toString() );
                        for ( Subsumer lcs : lcsList ) {
                                tracer.append( "Lowest Common Subsumer(s): ");
                                tracer.append( db.conceptToString( lcs.subsumer.getSynset() )+" (Length="+lcs.length+")\n" );
                        }
                }
                                
                return new Relatedness( score, tracer.toString(), null );
        }
        
        @Override
        public List<POS[]> getPOSPairs() {
                return posPairs;
        }

}
