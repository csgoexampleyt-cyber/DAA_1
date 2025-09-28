# Design and Analysis of Algorithms

Architecture Notes
Depth Control Mechanisms
MergeSort:
Recursion Depth: Controlled by dividing array into halves (log₂n depth)
Optimization: Switches to insertion sort for small subarrays (≤16 elements)
Allocations: Uses a single buffer array allocated once (O(n) space)

QuickSort:
Recursion Depth: Randomized pivot selection prevents worst-case O(n) depth
Tail Recursion: Processes smaller partition first to limit stack depth
Allocations: In-place partitioning (O(1) additional space)

DeterministicSelect:
Recursion Depth: Median-of-medians guarantees O(log n) depth
Base Case: Direct sorting for small arrays (<5 elements)
Allocations: Creates median arrays (O(n) space)

ClosestPair:
Recursion Depth: Divide-and-conquer with strip processing (log n depth)
Optimization: Brute force for small point sets (≤3 points)
Allocations: Temporary arrays for strip processing (O(n) space)

Allocation Control
MergeSort: Single O(n) buffer reused across recursive calls
QuickSort: No additional allocations beyond stack frames
DeterministicSelect: Recursive median arrays (geometrically decreasing)
ClosestPair: Strip arrays for candidate point processing

Recurrence Analysis
MergeSort
Recurrence: T(n) = 2T(n/2) + O(n)
Method: Master Theorem Case 2
Result: Θ(n log n)
Intuition: Even splitting with linear merge work gives balanced divide-and-conquer

QuickSort
Recurrence: T(n) = T(k) + T(n-k-1) + O(n) where k is partition size
Method: Expected case analysis with random pivots
Result: Θ(n log n) expected, O(n²) worst-case
Intuition: Random pivots give balanced partitions on average

DeterministicSelect
Recurrence: T(n) = T(n/5) + T(7n/10) + O(n)
Method: Akra-Bazzi method
Result: Θ(n) worst-case
Intuition: Median-of-medians guarantees good pivot, eliminating large partitions

ClosestPair
Recurrence: T(n) = 2T(n/2) + O(n log n)
Method: Modified Master Theorem
Result: Θ(n log² n)
Intuition: Strip processing adds log n factor to standard divide-and-conquer



MergeSort shows consistent n log n growth

QuickSort has higher variance due to random pivots(Assignment told me to randomize pivots)

DeterministicSelect has larger constants but linear growth

ClosestPair shows n log² n scaling clearly

<img width="592" height="366" alt="image" src="https://github.com/user-attachments/assets/a5706539-2d5b-447e-bf6a-6dbc6f3b09ca" />
