package me.passin.taskscheduler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author: zbb 33775
 * @date: 2019/4/5 22:23
 * @desc:
 */
public final class DirectedAcyclicGraph<T> {

    private final SimpleArrayMap<T, ArrayList<T>> mGraph;
    private final ArrayList<T> mSortResult;
    private final HashSet<T> mSortTmpMarked;

    public DirectedAcyclicGraph(int taskAmount) {
        mGraph = new SimpleArrayMap<>(taskAmount);
        mSortResult = new ArrayList(taskAmount);
        mSortTmpMarked = new HashSet<>(taskAmount);
    }

    private void addNode(@NonNull T node) {
        if (!this.mGraph.containsKey(node)) {
            mGraph.put(node, null);
        }
    }

    public boolean contains(@NonNull T node) {
        return this.mGraph.containsKey(node);
    }

    public void addEdge(@NonNull T node, @NonNull T incomingEdge) {
        addNode(node);
        addNode(incomingEdge);
        ArrayList<T> edges = this.mGraph.get(node);
        if (edges == null) {
            edges = new ArrayList<>();
            this.mGraph.put(node, edges);
        }

        edges.add(incomingEdge);
    }

    @Nullable
    public List<T> getIncomingEdges(@NonNull T node) {
        return mGraph.get(node);
    }

    @Nullable
    public List<T> getOutgoingEdges(@NonNull T node) {
        ArrayList<T> result = null;
        int i = 0;

        for (int size = this.mGraph.size(); i < size; ++i) {
            ArrayList<T> edges = this.mGraph.valueAt(i);
            if (edges != null && edges.contains(node)) {
                if (result == null) {
                    result = new ArrayList();
                }

                result.add(this.mGraph.keyAt(i));
            }
        }

        return result;
    }

    public boolean hasOutgoingEdges(@NonNull T node) {
        int i = 0;

        for (int size = this.mGraph.size(); i < size; ++i) {
            ArrayList<T> edges = this.mGraph.valueAt(i);
            if (edges != null && edges.contains(node)) {
                return true;
            }
        }

        return false;
    }

    public void clear() {
        mGraph.clear();
    }

    @NonNull
    public ArrayList<T> getSortedList() {
        mSortResult.clear();
        mSortTmpMarked.clear();
        int i = 0;

        for (int size = this.mGraph.size(); i < size; ++i) {
            dfs(mGraph.keyAt(i), mSortResult, mSortTmpMarked);
        }

        return mSortResult;
    }

    private void dfs(T node, ArrayList<T> result, HashSet<T> tmpMarked) {
        if (!result.contains(node)) {
            if (tmpMarked.contains(node)) {
                throw new RuntimeException("This graph contains cyclic dependencies");
            } else {
                tmpMarked.add(node);
                ArrayList<T> edges = mGraph.get(node);
                if (edges != null) {
                    int i = 0;

                    for (int size = edges.size(); i < size; ++i) {
                        dfs(edges.get(i), result, tmpMarked);
                    }
                }

                tmpMarked.remove(node);
                result.add(node);
            }
        }
    }

    int size() {
        return this.mGraph.size();
    }


}

