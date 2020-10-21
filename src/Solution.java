import java.util.PriorityQueue;

public class Solution {
    PriorityQueue<Integer> maxHeap;
    PriorityQueue<Integer> minHeap;

    public Solution(){
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
        minHeap = new PriorityQueue<>((a, b) -> a - b);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int i = 0, j = 0;
        while(i < nums1.length && j < nums2.length){
            if(nums1[i] <= nums2[j]){
                insert(nums1, i);
                i++;
            }else{
                insert(nums2, j);
                j++;
            }
        }

        while(i < nums1.length){
            insert(nums1, i);
            i++;
        }

        while(j < nums2.length){
            insert(nums2, j);
            j++;
        }

        if(maxHeap.size() == minHeap.size()){
            double sum = maxHeap.peek() + minHeap.peek();
            return  sum/ 2;
        }

        return maxHeap.peek();
    }

    public void insert(int[] nums, int index){
        if(maxHeap.isEmpty() || nums[index] < maxHeap.peek()){
            maxHeap.add(nums[index]);
        }else{
            minHeap.add(nums[index]);
        }
        if(minHeap.size() - maxHeap.size() >= 1){
            maxHeap.add(minHeap.poll());
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2};
        int[] nums2 = {3, 4};
        Solution solution = new Solution();

        System.out.println(solution.findMedianSortedArrays(nums1, nums2));
    }
}
