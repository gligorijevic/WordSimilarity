/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webevo.logic.utility.schema.mapper.wordsimilarity;

import java.util.ArrayList;
import java.util.List;
import webevo.domain.mapper.Word;
import webevo.domain.mapper.WordTypeOntology;

/**
 *
 * @author Djordje
 */
public class JiangConrath extends WordSimilarityMeasure{
    protected static double min = 0; // or -Double.MAX_VALUE ?
	protected static double max = Double.MAX_VALUE;

	@SuppressWarnings("serial")
	private static List<char[]> posPairs = new ArrayList<char[]>(){{
		add(new char[]{WordTypeOntology.noun,WordTypeOntology.noun});
		add(new char[]{WordTypeOntology.verb,WordTypeOntology.verb});
	}};

	public JiangConrath(ILexicalDatabase db) {
		super(db);
	}

	@Override
	protected double calcRelatedness( Word synset1, Word synset2 ) {
		StringBuilder tracer = new StringBuilder();
		if ( synset1 == null || synset2 == null ) return new Relatedness( min, null, illegalSynset );
		//Don't short circuit here, calculate the real value!
		//if ( synset1.getSynset().equals( synset2.getSynset() ) ) return new Relatedness( max );

		StringBuilder subTracer = enableTrace ? new StringBuilder() : null;

		List<Subsumer> lcsList = ICFinder.getInstance().getLCSbyIC( pathFinder,
			synset1, synset2, subTracer );

		if ( lcsList.size() == 0 ) return new Relatedness( min, tracer.toString(), null );
		if ( enableTrace ) {
			tracer.append( subTracer.toString() );
			for ( Subsumer lcs : lcsList ) {
				tracer.append( "Lowest Common Subsumer(s): ");
				tracer.append( db.conceptToString( lcs.subsumer.getSynset() )+" (IC="+lcs.ic+")\n" );
			}
		}

		Subsumer subsumer = lcsList.get(0);
		String lcsSynset = subsumer.subsumer.getSynset();
		double lcsIc = subsumer.ic;

		/* Commented out as maxScore is not used */
		//int lcsFreq = ICFinder.getInstance().getFrequency( lcsSynset );
		//double maxScore;

		Concept rootSynset = pathFinder.getRoot( lcsSynset );
		rootSynset.setPos( subsumer.subsumer.getPos() );
		int rootFreq = ICFinder.getInstance().getFrequency( rootSynset );

		if ( rootFreq > 0 ) {
			/* Commented out as maxScore is not used */
			//maxScore = 2D * -Math.log( 0.001D / (double)rootFreq ) + 1; // add-1 smoothing
		} else {
			return new Relatedness( min, tracer.toString(), null );
		}

		/* Comments from WordNet::Similarity::jcn.pm:
		 * Foreach lowest common subsumer...
		 * Find the minimum jcn distance between the two subsuming concepts...
		 * Making sure that neither of the 2 concepts have 0 infocontent
		 */

		double ic1 = ICFinder.getInstance().ic( pathFinder, synset1 );
		double ic2 = ICFinder.getInstance().ic( pathFinder, synset2 );

		if ( enableTrace ) {
			tracer.append( "Concept1: "+db.conceptToString(synset1.getSynset())+" (IC="+ic1+")\n" );
			tracer.append( "Concept2: "+db.conceptToString(synset2.getSynset())+" (IC="+ic2+")\n" );
		}

		double distance = 0D;
		if ( ic1>0 && ic2>0 ) {
			distance = ic1 + ic2 - ( 2 * lcsIc );
		} else {
			return new Relatedness( min, tracer.toString(), null );
		}

		/* Comments from WordNet::Similarity jcn.pm:
		 * Now if distance turns out to be 0...
		 * implies ic1 == ic2 == ic3 (most probably all three represent
		 * the same concept)... i.e. maximum relatedness... i.e. infinity...
		 * We'll return the maximum possible value ("Our infinity").
		 * Here's how we got our infinity...
		 * distance = ic1 + ic2 - (2 x ic3)
		 * Largest possible value for (1/distance) is infinity, when distance = 0.
		 * That won't work for us... Whats the next value on the list...
		 * the smallest value of distance greater than 0...
		 * Consider the formula again... distance = ic1 + ic2 - (2 x ic3)
		 * We want the value of distance when ic1 or ic2 have information content
		 * slightly more than that of the root (ic3)... (let ic2 == ic3 == 0)
		 * Assume frequency counts of 0.01 less than the frequency count of the
		 * root for computing ic1...
		 * sim = 1/ic1
		 * sim = 1/(-log((freq(root) - 0.01)/freq(root)))
		 */

		double score = 0D;

		if ( distance == 0D ) {
			if ( rootFreq > 0.01D ) {
				score = 1D / -Math.log( ((double)rootFreq - 0.01D) / (double)rootFreq );
			} else {
				return new Relatedness( min, tracer.toString(), null );
			}
		} else { // happy path
			score = 1D / distance;
		}

		return new Relatedness( score, tracer.toString(), null );
	}

	@Override
	public List<char[]> getWordTypes() {
		return posPairs;
	}
}
