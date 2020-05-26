package xmlpulltreebuilder;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class EmptyAttributeList extends AttributeList {

	static final EmptyAttributeList INSTANCE = new EmptyAttributeList();

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public void append(Attribute attr) {
		throw new UnsupportedOperationException("EmptyAttributeList is immutable");
	}

	@Override
	public void insert(Attribute attr) {
		throw new UnsupportedOperationException("EmptyAttributeList is immutable");
	}

	@Override
	public Iterator<Attribute> iterator() {
		return new Iterator<Attribute>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public Attribute next() {
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("EmptyAttributeList is immutable");
			}
		};
	}

}
