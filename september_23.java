
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

    ////********************************************************************************************************* */
    //!10/9/23
    // refeered this video best soln (https://www.youtube.com/watch?v=p1tvA-eQFqk)
    public int countOrders(int n) {
        int mod = (int) Math.pow(10,9)+7;
        long[] dp = new long[501];
        dp[1]=1L;
        dp[2]=6L;
        for(int i=3;i<=n;i++){
            int getfirstpossiblecombo = 2*i-1;
            dp[i] = ((dp[i-1]*(getfirstpossiblecombo+1)*getfirstpossiblecombo)/2)%mod;
        }
        return (int) dp[n];
    }

    ////********************************************************************************************************* */
    //!11/9/23
    // why this is even considered as mid lvl prblm ?
    public List<List<Integer>> groupThePeople(int[] arr) {
        HashMap<Integer,List<Integer>> hash = new HashMap<>();
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            List<Integer> lst = hash.getOrDefault(arr[i],new ArrayList<>());
            if(arr[i]==lst.size()){
                res.add(lst);
                lst = new ArrayList<>();
            }
            lst.add(i);
            hash.put(arr[i],lst);
        }

        for(Integer i:hash.keySet()){
            res.add(hash.get(i));
        }
        return res;
    }

    ////********************************************************************************************************* */
    //!12/9/23
    public int minDeletions(String s) {
        int max = 0;
        HashMap<Character,Integer> hash = new HashMap<>();
        for(int i=0;i<s.length();i++){
            int x = hash.getOrDefault(s.charAt(i),0)+1;
            max = Math.max(max,x);
            hash.put(s.charAt(i),x);
        }
        int tutsum = 0;
        HashSet<Integer> set = new HashSet<>();
        for(Character c:hash.keySet()){
            if(set.contains(hash.get(c))){
                int val = hash.get(c)-1;
                while(set.contains(val)){
                    val--;
                }
                set.add(val);
            }
            set.add(hash.get(c));
        }
        for(Integer i:set){
            if(i>0){
            tutsum+=i;}
        }
        // System.out.println(hash+" "+s.length()+" "+tutsum+" "+set);
        return s.length()-tutsum;
    }
    ////********************************************************************************************************* */
    //!13/9/23
    // did greedy approach but poor implementation 
    // https://www.youtube.com/watch?v=h6_lIwZYHQw (good approach)
    public int candy(int[] ratings) {
        if(ratings.length==1){
            return 1;
        }
        int[] resL = new int[ratings.length];
        int[] resR = new int[ratings.length];
        Arrays.fill(resL,1);
        Arrays.fill(resR,1);

        for(int i=1;i<ratings.length;i++){
            if(ratings[i]>ratings[i-1]){
                resL[i]=resL[i-1]+1;
            }
        }
         for(int i=ratings.length-1;i>=1;i--){
            if(ratings[i]<ratings[i-1]){
                resR[i-1]=resR[i]+1;
            }
        }
        int sum =0;
        for(int i=0;i<ratings.length;i++){
            sum+=Math.max(resR[i],resL[i]);
            // System.out.println(Math.max(resR[i],resL[i]));
        }
        return sum;
    }
    ////********************************************************************************************************* */
    //!14/9/23
    // Note: Choosing proper dS is imp 
    // Attempted without Stack but too much complex
    public List<String> findItinerary(List<List<String>> tickets) {
        HashMap<String,PriorityQueue<String>> hash = new HashMap<>();
        for(List<String> i:tickets){
            PriorityQueue<String> lst = hash.getOrDefault(i.get(0),new PriorityQueue<String>());
            lst.add(i.get(1));
            hash.put(i.get(0),lst);
        }
        List<String> res = new ArrayList<>();
        Stack<String> stk = new Stack<>();
        stk.push("JFK");
        // System.out.println(hash);
        while(!stk.empty()){
            // System.out.println(stk+" stack");
            // System.out.println(hash);
            String val = stk.peek();
            if(hash.containsKey(stk.peek())){
                PriorityQueue<String> maps = hash.get(stk.peek());
                // System.out.println(maps.isEmpty()+" "+maps);
                if(maps.isEmpty()){
                    res.add(stk.pop());
                    continue;
                }
                // System.out.println(val);
                stk.push(maps.poll());
                hash.put(val,maps);
            }else{
                res.add(stk.pop());
            }
        }
        Collections.reverse(res);
        return res;
    }
    ////********************************************************************************************************* */
    //!15/9/23
    // prims Algo to find minimum spanning tree in graph
    // for this question -->
    // 1. check all connected nodes to prime node find (start,end,cost)
    // 2. add them to priorty queue
    // 3. get least cost node which is not in checked hashSet add it to priorty
    // 4. poll the pq and get prime node and repeat
    // (https://www.youtube.com/watch?v=rnYBi9N_vw4&t=815s&pp=ygUQcHJpbSdzIGFsZ29yaXRobQ%3D%3D)
    public int minCostConnectPoints(int[][] points) {
        HashSet<Integer> hash = new HashSet<>();
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((x, y) -> x[2] - y[2]);
        pq.add(new int[] { 0, 0, 0 });
        int count = 0;
        while (hash.size() < points.length && !pq.isEmpty()) {
            int[] elem = pq.poll();
            // System.out.println(Arrays.toString(elem));
            int end = elem[1];
            int cost = elem[2];
            if (hash.contains(end)) {
                continue;
            }
            hash.add(elem[0]);
            count += cost;
            // pq.clear();
            for (int i = 0; i < points.length; i++) {
                if (!hash.contains(i)) {
                    pq.offer(new int[] { end, i, Math.abs(points[end][0] - points[i][0]) +
                            Math.abs(points[end][1] - points[i][1]) });
                }
            }
        }
        return count;
    }
    ////********************************************************************************************************* */
    //!16/9/23
    // using Dijkstra's Algorithm (hint:- min efforts path/ lesscost path / best effi path )
    public int minimumEffortPath_Dijkstra(int[][] arr) {
        if (arr.length == 1) {
            int max = Integer.MIN_VALUE;
            for (int[] i : arr) {
                for (int j = 1; j < i.length; j++) {
                    max = Math.max(max, Math.abs(i[j - 1] - i[j]));
                }
            }
            return max == Integer.MIN_VALUE ? 0 : max;
        }
        int[][] temp = new int[arr.length][arr[0].length];
        for (int[] row : temp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // System.out.println((y+1)+" "+arr[0].length+" "+x);
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((x, y) -> x[0] - y[0]);
        pq.add(new int[] { 0, 0, 0 });
        int dr[] = { -1, 0, 1, 0 };
        int dc[] = { 0, 1, 0, -1 };
        int n = arr.length;
        int m = arr[0].length;
        while (!pq.isEmpty()) {
            int[] it = pq.poll();
            int diff = it[0];
            int r = it[1];
            int c = it[2];
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    int newEffort = Math.max(Math.abs(arr[r][c] - arr[nr][nc]), diff);
                    if (newEffort < temp[nr][nc]) {
                        temp[nr][nc] = newEffort;
                        pq.add(new int[] { newEffort, nr, nc });
                    }
                }
            }
        }
        return temp[arr.length - 1][arr[0].length - 1];
    }
    
    // Using Binary Search (best soln)
    private boolean[][] visited;  // Visited cells tracker
    private int[] directions_x = {0, 1, -1, 0};  // Changes in x coordinate for four directions
    private int[] directions_y = {1, 0, 0, -1};  // Changes in y coordinate for four directions
    private int numRows, numCols;  // Number of rows and columns in the matrix

    private void dfs_binarySearch(int x, int y, int limitEffort, int[][] heights) {
        if (visited[x][y])
            return;
        visited[x][y] = true;

        // Stop if we've reached the bottom-right cell
        if (x == numRows - 1 && y == numCols - 1)
            return;

        // Explore each direction (up, down, left, right)
        for (int i = 0; i < 4; i++) {
            int new_x = x + directions_x[i];
            int new_y = y + directions_y[i];

            // Check if the new coordinates are within bounds
            if (new_x < 0 || new_x >= numRows || new_y < 0 || new_y >= numCols)
                continue;

            // Go to next cell if the effort is within limit
            int newEffort = Math.abs(heights[x][y] - heights[new_x][new_y]);
            if (newEffort <= limitEffort)
                dfs_binarySearch(new_x, new_y, limitEffort, heights);
        }
    }

    public int minimumEffortPath(int[][] heights) {
        numRows = heights.length;
        numCols = heights[0].length;

        // Initialize visited array
        visited = new boolean[numRows][numCols];

        // Bound for our binary search
        int lowerLimit = 0, upperLimit = 1_000_000;

        while (lowerLimit < upperLimit) {
            int mid = (upperLimit + lowerLimit) / 2;
            for (boolean[] row : visited) {
                Arrays.fill(row, false);
            }

            dfs_binarySearch(0, 0, mid, heights);

            if (visited[numRows - 1][numCols - 1])
                upperLimit = mid;
            else
                lowerLimit = mid + 1;
        }

        return lowerLimit;
    }


    
}