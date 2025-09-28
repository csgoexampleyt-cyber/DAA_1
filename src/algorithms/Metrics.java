package algorithms;

public class Metrics {
    private long comparisons;
    private long allocations;
    private int maxRecursionDepth;
    private int currentRecursionDepth;
    private long startTime;
    private long endTime;

    public Metrics() {
        reset();
    }

    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxRecursionDepth = 0;
        currentRecursionDepth = 0;
        startTime = 0;
        endTime = 0;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public void incrementComparisons() { comparisons++; }
    public void incrementComparisons(long count) { comparisons += count; }
    public void incrementAllocations(long count) { allocations += count; }

    public void enterRecursion() {
        currentRecursionDepth++;
        maxRecursionDepth = Math.max(maxRecursionDepth, currentRecursionDepth);
    }

    public void exitRecursion() {
        currentRecursionDepth--;
    }

    public long getComparisons() { return comparisons; }
    public long getAllocations() { return allocations; }
    public int getMaxRecursionDepth() { return maxRecursionDepth; }
    public int getCurrentRecursionDepth() { return currentRecursionDepth; }
}