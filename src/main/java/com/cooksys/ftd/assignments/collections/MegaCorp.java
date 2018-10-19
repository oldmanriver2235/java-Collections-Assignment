package com.cooksys.ftd.assignments.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	Set<Capitalist> hash_Set = new HashSet<Capitalist>();
	// static Map<FatCat, Set<Capitalist>> hierarchyMap = new HashMap<>();

	/**
	 * Adds a given element to the hierarchy.
	 * <p>
	 * 1. If the given element is already present in the hierarchy, do not add it
	 * and return false
	 * <p>
	 * 2. If the given element has a parent and the parent is not part of the
	 * hierarchy, add the parent and then add the given element
	 * <p>
	 * 3. If the given element has no parent but is a Parent itself, add it to the
	 * hierarchy
	 * <p>
	 * 4. If the given element has no parent and is not a Parent itself, do not add
	 * it and return false
	 *
	 * @param capitalist the element to add to the hierarchy 5. @return true if the
	 *                   element was added successfully, false otherwise
	 */
	@Override
	public boolean add(Capitalist capitalist) {

		if (capitalist == null) {
			return false;
		}
		if (capitalist instanceof WageSlave && !capitalist.hasParent()) {
			return false;
		}

		if (capitalist.hasParent()) {
			this.add(capitalist.getParent());
		}

		if (capitalist.hasParent() == false && capitalist instanceof FatCat) {
			hash_Set.add(capitalist);
		}

		return hash_Set.add(capitalist);
	}

	/**
	 * @param capitalist the element to search for
	 * @return true if the element has been added to the hierarchy, false otherwise
	 */
	@Override
	public boolean has(Capitalist capitalist) {

		if (hash_Set.contains(capitalist)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @return all elements in the hierarchy, or an empty set if no elements have
	 *         been added to the hierarchy
	 */
	@Override
	public Set<Capitalist> getElements() {
		return hash_Set;
	}

	/**
	 * @return all parent elements in the hierarchy, or an empty set if no parents
	 *         have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {

		if (hash_Set.isEmpty()) {
			return new HashSet<FatCat>();
		}

		Set<FatCat> throwAway = new HashSet<FatCat>();

		for (Capitalist c : this.hash_Set) {

			if (c instanceof FatCat && !this.getChildren((FatCat) c).isEmpty()) {
				throwAway.add((FatCat) c);
			}
		}
		return throwAway;
	}

	/*
	 * Result process(Child child) { Result result = new Result(); for (Parent
	 * parent : child.getParents()) { result.update(process(parent)); } return
	 * result; } }
	 */
	/**
	 * @param fatCat the parent whose children need to be returned
	 * @return all elements in the hierarchy that have the given parent as a direct
	 *         parent, or an empty set if the parent is not present in the hierarchy
	 *         or if there are no children for the given parent
	 */
	@Override
	public Set<Capitalist> getChildren(FatCat fatCat) {

		Set<Capitalist> list = new HashSet<Capitalist>();
		if (fatCat == null) {
			return list;
		}

		for (Capitalist c : this.hash_Set) {

			if (c.hasParent() == true && c.getParent() == fatCat) {
				list.add(c);

			}
		}
		return list;
	}

	/**
	 * @return a map in which the keys represent the parent elements in the
	 *         hierarchy, and the each value is a set of the direct children of the
	 *         associate parent, or an empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {

		Map<FatCat, Set<Capitalist>> hierarchyMap = new HashMap<FatCat, Set<Capitalist>>();

		if (hash_Set.isEmpty()) {
			return hierarchyMap;
		}

		for (FatCat f : this.getParents()) {

			hierarchyMap.put(f, this.getChildren(f));

		}
		return hierarchyMap;

	}
	/*
	 * if (map.containsValue(Object.fatCat) || map.containsCalue(Object.Capitalist))
	 * { return this.getChildren(fatCat); } else { return new HashMap<FatCat,
	 * Set<Capitalist>>(); }
	 */

	/**
	 * @param capitalist
	 * @return the parent chain of the given element, starting with its direct
	 *         parent, then its parent's parent, etc, or an empty list if the given
	 *         element has no parent or if its parent is not in the hierarchy
	 */
	@Override
	public List<FatCat> getParentChain(Capitalist capitalist) {
		List<FatCat> newArray = new ArrayList<FatCat>();

		if (capitalist == null || this.hash_Set.isEmpty())
			return newArray;

		if (capitalist.hasParent()) {
			newArray.add(capitalist.getParent());
			return this.getParentChain(newArray);
		} else {
			return newArray;
		}
	}

	private List<FatCat> getParentChain(List<FatCat> newArray) {
		if (newArray.get(newArray.size() - 1).hasParent()) {
			newArray.add(newArray.get(newArray.size() - 1).getParent());
			return this.getParentChain(newArray);
		} else {
			return newArray;
		}
	}

}
