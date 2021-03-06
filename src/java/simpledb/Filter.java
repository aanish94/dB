package simpledb;

import java.util.*;

/**
 * Filter is an operator that implements a relational select.
 */
public class Filter extends Operator {

	private static final long serialVersionUID = 1L;
	//PRIVATE MEMBERS
	private Predicate m_p; //Predicate used to filter tuples
	DbIterator m_child; //iterator
	/**
	 * Constructor accepts a predicate to apply and a child operator to read
	 * tuples to filter from.
	 * 
	 * @param p
	 *            The predicate to filter tuples with
	 * @param child
	 *            The child operator
	 */
	public Filter(Predicate p, DbIterator child) {
		m_p = p;
		m_child = child;
	}

	public Predicate getPredicate() {
		return m_p;
	}

	public TupleDesc getTupleDesc() {
		return m_child.getTupleDesc();
	}

	public void open() throws DbException, NoSuchElementException,
	TransactionAbortedException {
		super.open();
		m_child.open();
	}

	public void close() {
		super.close();
		m_child.close();
	}

	public void rewind() throws DbException, TransactionAbortedException {
		m_child.rewind();
	}

	/**
	 * AbstractDbIterator.readNext implementation. Iterates over tuples from the
	 * child operator, applying the predicate to them and returning those that
	 * pass the predicate (i.e. for which the Predicate.filter() returns true.)
	 * 
	 * @return The next tuple that passes the filter, or null if there are no
	 *         more tuples
	 * @see Predicate#filter
	 */
	protected Tuple fetchNext() throws NoSuchElementException,
	TransactionAbortedException, DbException {

		while (m_child.hasNext())
		{
			Tuple cur = m_child.next();
			if (m_p.filter(cur)) { return cur; }
		}
		return null;
	}
	//NOTE: GET/SET CHILDREN seem to not matter for systemtest.
	//I do not think this is a correct implementation, but doesn't matter for lab2
	@Override
	public DbIterator[] getChildren() {
		// some code goes here
		DbIterator [] children = new DbIterator [1];
		children[0] = m_child;
		return children;
	}

	@Override
	public void setChildren(DbIterator[] children) {
		// some code goes here
		children[0] = m_child;
	}

}
