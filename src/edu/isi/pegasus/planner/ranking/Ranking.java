/*
 * This file or a portion of this file is licensed under the terms of
 * the Globus Toolkit Public License, found in file GTPL, or at
 * http://www.globus.org/toolkit/download/license.html. This notice must
 * appear in redistributions of this file, with or without modification.
 *
 * Redistributions of this Software, with or without modification, must
 * reproduce the GTPL in: (1) the Software, or (2) the Documentation or
 * some other similar material which is provided with the Software (if
 * any).
 *
 * Copyright 1999-2004 University of Chicago and The University of
 * Southern California. All rights reserved.
 */

package edu.isi.pegasus.planner.ranking;

/**
 * A Data class that associates a DAX with the rank.
 *
 * @author Karan Vahi
 * @version $Revision$
 */
public class Ranking implements Comparable {

    /**
     * The name of the DAX.
     */
    private String mName;

    /**
     * Rank of the dax.
     */
    private long mRank;


    /**
     * The overloaded constructor.
     *
     * @param name  the name of the dax
     * @param rank  the rank
     */
    public Ranking( String name, long rank ) {
        mRank = rank;
        mName = name;
    }

    /**
     * Sets the rank.
     *
     * @param rank the rank.
     */
    public void setRank( long rank ){
        mRank = rank;
    }

    /**
     * Sets the name.
     *
     * @param name  the name of the dax
     */
    public void setName( String name ){
        mName = name;
    }


    /**
     * Returns the rank.
     *
     * @return the rank.
     */
    public long getRank(){
        return mRank;
    }

    /**
     * Returns the name of the dax.
     *
     * @return the name
     */
    public String getName(){
        return mName;
    }

    /**
     * Returns a textual description.
     *
     * @return String
     */
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append( mName ).append( ":" ).append( "DAX" ).append( ":" ).append( mRank );
        return sb.toString();
    }

    /**
     * Implementation of the {@link java.lang.Comparable} interface.
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object. The
     * definitions are compared by their type, and by their short ids.
     *
     * @param o is the object to be compared
     * @return a negative number, zero, or a positive number, if the
     * object compared against is less than, equals or greater than
     * this object.
     * @exception ClassCastException if the specified object's type
     * prevents it from being compared to this Object.
     */
    public int compareTo( Object o ){
        if ( o instanceof Ranking ) {
            Ranking r = ( Ranking ) o;
            return (int)(this.getRank() - r.getRank());
        } else {
            throw new ClassCastException( "object is not a Ranking" );
        }
    }

    public boolean equals( Object o ){
        boolean result = false;
        if( o instanceof Ranking ){
            Ranking r = ( Ranking ) o;
            result = ( r.getName().equals( this.getName() ) ) &&
                     ( r.getRank() == this.getRank() );
        }
        return result;
    }

}
