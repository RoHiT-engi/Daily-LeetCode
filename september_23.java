
import java.util.*;
class September_23{
    public static void main(String[] args) {
        // System.out.println();
        System.out.println(combinationSum4(new int[] { 2,1,3 }, 35));
    }

    //! 6/9/23

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
   }

   public ListNode[] splitListToParts(ListNode head, int k) {
       // Performance efficient not memory (remove List)
       ListNode[] res = new ListNode[k];
       List<ListNode> lst = new ArrayList<>();
       ListNode temp = head;
       while (temp != null) {
           lst.add(temp);
           temp = temp.next;
       }
       int count = lst.size();
       if (lst.size() < k) {
           for (int i = 0; i < k; i++) {
               if (i < lst.size() && lst.get(i) != null) {
                   res[i] = lst.get(i);
                   res[i].next = null;
               } else {
                   res[i] = null;
               }
           }
       } else {
           temp = head;
           res[0] = head;
           int curr = 0;

           for (int i = 1; i < res.length; i++) {
               int x = count / k;
               int y = count % k;
               if (y > 0) {
                   y = 1;
               }
               lst.get(curr + x + y - 1).next = null;
               res[i] = lst.get(curr + x + y);
               curr = curr + x + y;
               count = count - x - y;
               k--;
           }
       }
       return res;
   }

    ////********************************************************************************************************* */
    //! 7/9/23

    //?My Approach(gadha majduri)
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head.next == null || left == right) {
            return head;
        }
        ListNode ittr = head;
        int count = 1;
        ListNode attach1 = null;
        while (count != left - 1) {
            ittr = ittr.next;
            count++;
        }
        attach1 = left == 1 ? null : ittr;
        ittr = ittr.next;
        ListNode prev = null;
        ListNode next = null;
        count = left;
        while (count <= right) {
            next = ittr.next;
            ittr.next = prev;
            prev = ittr;
            ittr = next;
            count++;
        }
        if (left != 1) {
            attach1.next = prev;
        }
        ListNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = ittr;
        return left == 1 ? prev : head;
    }
    
    //? Good Approach
    public ListNode reverseBetween_goodApproach(ListNode head, int left, int right) {
        ListNode temp = new ListNode(-1);
        ListNode prev = temp;
        temp.next = head;

        for (int i = 0; i < left - 1; i++) {
            prev = prev.next;
        }

        ListNode cur = prev.next;

        for (int i = 0; i < right - left; i++) {
            ListNode ptr = prev.next;
            prev.next = cur.next;
            cur.next = cur.next.next;
            prev.next.next = ptr;
        }

        return temp.next;
    }

    ////********************************************************************************************************* */
    //!8/9/23

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        result.add(lst);
        if(numRows==1) {
            return result;
        }
        else{
            return getem1(result,2,numRows+1);}
    }
    static List<List<Integer>> getem1(List<List<Integer>> mainLst,int index,int numRows){
        if(index == numRows){
            return mainLst;
        }
        List<Integer> lst = new ArrayList<>();
        List<Integer> prevlst = mainLst.get(mainLst.size()-1);
        int counter = 0;
        for(int i=0;i<index;i++){
            if(i==0 || i==(index-1)){
                lst.add(1);
            }else{
                int val = prevlst.get(counter)+prevlst.get(++counter);
                lst.add(val);
            }
        }
        mainLst.add(lst);
        return getem1(mainLst,index+1,numRows);
    }

    ////********************************************************************************************************* */
    //!9/9/23
    // Non DP Approach (not good for higher target)
    static int count = 0;
    static int combinationSum4(int[] nums, int target) {
        for(int i=0;i<nums.length;i++){
            getem(nums,target,0+nums[i]);
        }
        return count;
    }

    static void getem(int[] nums, int target, int sum) {
        if (sum > target) {
            return;
        }

        if (sum == target) {
            count++;
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            getem(nums, target, nums[i] + sum);
        }
    }
    
    // the love babbar Approach

    public int combinationSum4_BetterApproach(int[] nums, int target) {
       int[] dp = new int[target+1];
       Arrays.fill(dp,-1);
       dp[0] = 1;
       return getem(nums,target,dp);
    //    return dp[target-1];
    }

    public int getem(int[] nums, int target,int[] dp) {
        if(target==0){
            return 1;
        }
        if(target<0){
            return 0;
        }
        if(dp[target]!=-1){
            return dp[target];
        }
        int ans = 0;
        for(int i:nums){
            ans += getem(nums,target-i,dp);
        }
        dp[target] = ans;
        return dp[target];
    }
}