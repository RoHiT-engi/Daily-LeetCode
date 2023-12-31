
import java.util.*;

class September_23 {
    public static void main(String[] args) {
        // System.out.println();
        System.out.println(combinationSum4(new int[] { 2, 1, 3 }, 35));
    }

    //! 6/9/23

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
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
        if (numRows == 1) {
            return result;
        } else {
            return getem1(result, 2, numRows + 1);
        }
    }

    static List<List<Integer>> getem1(List<List<Integer>> mainLst, int index, int numRows) {
        if (index == numRows) {
            return mainLst;
        }
        List<Integer> lst = new ArrayList<>();
        List<Integer> prevlst = mainLst.get(mainLst.size() - 1);
        int counter = 0;
        for (int i = 0; i < index; i++) {
            if (i == 0 || i == (index - 1)) {
                lst.add(1);
            } else {
                int val = prevlst.get(counter) + prevlst.get(++counter);
                lst.add(val);
            }
        }
        mainLst.add(lst);
        return getem1(mainLst, index + 1, numRows);
    }

    ////********************************************************************************************************* */
    //!9/9/23
    // Non DP Approach (not good for higher target)
    static int count = 0;

    static int combinationSum4(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            getem(nums, target, 0 + nums[i]);
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
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        return getem(nums, target, dp);
    }

    public int getem(int[] nums, int target, int[] dp) {
        if (target == 0) {
            return 1;
        }
        if (target < 0) {
            return 0;
        }
        if (dp[target] != -1) {
            return dp[target];
        }
        int ans = 0;
        for (int i : nums) {
            ans += getem(nums, target - i, dp);
        }
        dp[target] = ans;
        return dp[target];
    }

    ////********************************************************************************************************* */
    //!10/9/23
    // refeered this video best soln (https://www.youtube.com/watch?v=p1tvA-eQFqk)
    public int countOrders(int n) {
        int mod = (int) Math.pow(10, 9) + 7;
        long[] dp = new long[501];
        dp[1] = 1L;
        dp[2] = 6L;
        for (int i = 3; i <= n; i++) {
            int getfirstpossiblecombo = 2 * i - 1;
            dp[i] = ((dp[i - 1] * (getfirstpossiblecombo + 1) * getfirstpossiblecombo) / 2) % mod;
        }
        return (int) dp[n];
    }

    ////********************************************************************************************************* */
    //!11/9/23
    // why this is even considered as mid lvl prblm ?
    public List<List<Integer>> groupThePeople(int[] arr) {
        HashMap<Integer, List<Integer>> hash = new HashMap<>();
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            List<Integer> lst = hash.getOrDefault(arr[i], new ArrayList<>());
            if (arr[i] == lst.size()) {
                res.add(lst);
                lst = new ArrayList<>();
            }
            lst.add(i);
            hash.put(arr[i], lst);
        }

        for (Integer i : hash.keySet()) {
            res.add(hash.get(i));
        }
        return res;
    }

    ////********************************************************************************************************* */
    //!12/9/23
    public int minDeletions(String s) {
        int max = 0;
        HashMap<Character, Integer> hash = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            int x = hash.getOrDefault(s.charAt(i), 0) + 1;
            max = Math.max(max, x);
            hash.put(s.charAt(i), x);
        }
        int tutsum = 0;
        HashSet<Integer> set = new HashSet<>();
        for (Character c : hash.keySet()) {
            if (set.contains(hash.get(c))) {
                int val = hash.get(c) - 1;
                while (set.contains(val)) {
                    val--;
                }
                set.add(val);
            }
            set.add(hash.get(c));
        }
        for (Integer i : set) {
            if (i > 0) {
                tutsum += i;
            }
        }
        // System.out.println(hash+" "+s.length()+" "+tutsum+" "+set);
        return s.length() - tutsum;
    }

    ////********************************************************************************************************* */
    //!13/9/23
    // did greedy approach but poor implementation 
    // https://www.youtube.com/watch?v=h6_lIwZYHQw (good approach)
    public int candy(int[] ratings) {
        if (ratings.length == 1) {
            return 1;
        }
        int[] resL = new int[ratings.length];
        int[] resR = new int[ratings.length];
        Arrays.fill(resL, 1);
        Arrays.fill(resR, 1);

        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                resL[i] = resL[i - 1] + 1;
            }
        }
        for (int i = ratings.length - 1; i >= 1; i--) {
            if (ratings[i] < ratings[i - 1]) {
                resR[i - 1] = resR[i] + 1;
            }
        }
        int sum = 0;
        for (int i = 0; i < ratings.length; i++) {
            sum += Math.max(resR[i], resL[i]);
            // System.out.println(Math.max(resR[i],resL[i]));
        }
        return sum;
    }

    ////********************************************************************************************************* */
    //!14/9/23
    // Note: Choosing proper dS is imp 
    // Attempted without Stack but too much complex
    public List<String> findItinerary(List<List<String>> tickets) {
        HashMap<String, PriorityQueue<String>> hash = new HashMap<>();
        for (List<String> i : tickets) {
            PriorityQueue<String> lst = hash.getOrDefault(i.get(0), new PriorityQueue<String>());
            lst.add(i.get(1));
            hash.put(i.get(0), lst);
        }
        List<String> res = new ArrayList<>();
        Stack<String> stk = new Stack<>();
        stk.push("JFK");
        // System.out.println(hash);
        while (!stk.empty()) {
            // System.out.println(stk+" stack");
            // System.out.println(hash);
            String val = stk.peek();
            if (hash.containsKey(stk.peek())) {
                PriorityQueue<String> maps = hash.get(stk.peek());
                // System.out.println(maps.isEmpty()+" "+maps);
                if (maps.isEmpty()) {
                    res.add(stk.pop());
                    continue;
                }
                // System.out.println(val);
                stk.push(maps.poll());
                hash.put(val, maps);
            } else {
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
    private boolean[][] visited; // Visited cells tracker
    private int[] directions_x = { 0, 1, -1, 0 }; // Changes in x coordinate for four directions
    private int[] directions_y = { 1, 0, 0, -1 }; // Changes in y coordinate for four directions
    private int numRows, numCols; // Number of rows and columns in the matrix

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

    ////********************************************************************************************************* */
    //!17/9/23

    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        int allVisited = (1 << n) - 1;
        Queue<int[]> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < n; i++) {
            queue.offer(new int[] { 1 << i, i, 0 });
            visited.add((1 << i) * 16 + i);
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if (cur[0] == allVisited) {
                return cur[2];
            }

            for (int neighbor : graph[cur[1]]) {
                int newMask = cur[0] | (1 << neighbor);
                int hashValue = newMask * 16 + neighbor;

                if (!visited.contains(hashValue)) {
                    visited.add(hashValue);
                    queue.offer(new int[] { newMask, neighbor, cur[2] + 1 });
                }
            }
        }

        return -1;
    }

    ////********************************************************************************************************* */
    //!18/9/23
    class BruhComparator implements Comparator<int[]> {
        // override the compare() method
        public int compare(int[] l1, int[] l2) {
            if (l1[1] == l2[1]) {
                return 0;
            } else if (l1[1] > l2[1]) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public int[] kWeakestRows(int[][] mat, int k) {
        ArrayList<int[]> lst = new ArrayList<>();
        int[] res = new int[k];
        for (int i = 0; i < mat.length; i++) {
            int count = 0;
            for (int j : mat[i]) {
                if (j == 1) {
                    count++;
                } else {
                    break;
                }
            }
            lst.add(new int[] { i, count });
        }
        Collections.sort(lst, new BruhComparator());
        for (int i = 0; i < k; i++) {
            res[i] = lst.get(i)[0];
        }
        return res;
    }

    ////********************************************************************************************************* */
    //!19/9/23
    //!Floyd's Cycle Detection
    // Approach
    /* 1. Initialize two pointers, tortoise and hare, at the beginning of the array.
       2. Move tortoise one step at a time and hare two steps at a time.
       3. If there is a duplicate number in the array, tortoise and hare will meet at some index in the array.
       4. Then reset tortoise to the beginning of the array and move both tortoise and hare one step at a time.
       5. tortoise and hare will meet again at the index of the duplicate number.*/
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        do {
            slow = nums[slow];
            fast = nums[fast];
        } while (slow != fast);
        return slow;
    }

    ////********************************************************************************************************* */
    //!20/9/23
    // TODO
    // Brute Force (Dp)
    public int minOperations_Dp(int[] nums, int x) {
        int n = nums.length;
        int dp[][] = new int[n + 1][n + 1];

        for (int[] arr : dp)
            Arrays.fill(arr, -1);

        int temp = helper(nums, x, 0, n - 1, n, dp);

        return temp == 1000000 ? -1 : temp;
    }

    public int helper(int[] nums, int x, int i, int j, int n, int dp[][]) {
        if (x == 0)
            return 0;
        if (i > j || i >= n || j < 0 || x < 0) {
            return 1000000;
        }
        if (dp[i][j] != -1)
            return dp[i][j];

        int ans = 1000000;
        ans = Math.min(ans, 1 + helper(nums, x - nums[i], i + 1, j, n, dp));
        ans = Math.min(ans, 1 + helper(nums, x - nums[j], i, j - 1, n, dp));
        return dp[i][j] = ans;
    }

    //  find by creating subarray of n-x
    public int minOperations_SubArraySoln(int[] nums, int x) {
        int start = 0;
        int end = nums.length - 1;
        if (nums[start] > x && nums[end] > x) {
            return -1;
        }
        int sum = 0;
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            hash.put(sum, i);
        }
        int val = 0;
        int longest = 0;
        sum -= x;
        hash.put(0, 0);
        for (int i = 0; i < nums.length; i++) {
            val = val + nums[i];
            if (hash.containsKey(val - sum)) {
                if (val - sum == 0) {
                    longest = Math.max(longest, i - hash.get(val - sum) + 1);
                } else {
                    longest = Math.max(longest, i - hash.get(val - sum));
                }
            }
        }
        return longest == 0 ? (sum == 0 ? nums.length : -1) : nums.length - longest;
    }

    ////********************************************************************************************************* */
    //!21/9/23
    // TODO: 
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length;
        if (n1 > n2)
            return findMedianSortedArrays(nums2, nums1);
        
        int n = n1 + n2;
        int left = (n1 + n2 + 1) / 2; 
        int low = 0, high = n1;
        
        while (low <= high) {
            int mid1 = (low + high) >> 1; 
            int mid2 = left - mid1; 
            
            int l1 = Integer.MIN_VALUE, l2 = Integer.MIN_VALUE, r1 = Integer.MAX_VALUE, r2 = Integer.MAX_VALUE;
            
            if (mid1 < n1)
                r1 = nums1[mid1];
            if (mid2 < n2)
                r2 = nums2[mid2];
            if (mid1 - 1 >= 0)
                l1 = nums1[mid1 - 1];
            if (mid2 - 1 >= 0)
                l2 = nums2[mid2 - 1];
            
            if (l1 <= r2 && l2 <= r1) {
                if (n % 2 == 1)
                    return Math.max(l1, l2);
                else
                    return ((double)(Math.max(l1, l2) + Math.min(r1, r2))) / 2.0;
            }
            else if (l1 > r2) {
                high = mid1 - 1;
            }
            else {
                low = mid1 + 1;
            }
        }
        
        return 0;

    }

    
    ////********************************************************************************************************* */
    //!22/9/23

    public boolean isSubsequence(String s, String t) {
        int pt = 0;
        for (int i = 0; i < t.length(); i++) {
            if (pt == s.length()) {
                return true;
            }
            if (s.charAt(pt) == t.charAt(i)) {
                pt++;
            }
        }
        return pt == s.length() ? true : false;
    }
    
    
    ////********************************************************************************************************* */
    //!23/9/23 
    // TODO: Striver dp vids try (37 - 42)
    //? Good Soln
    public int longestStrChain_Dp(String[] words) {
        // HashSet<String> hash = new HashSet<>();
        Arrays.sort(words,Comparator.comparing(s->s.length()));
        int dp[] = new int[words.length];
        Arrays.fill(dp,1);
        int max = 0;
        for(int i=0;i<words.length;i++){
            for(int j=0;j<i;j++){
                if(compare(words[i],words[j])){
                    // System.out.println(i+" "+j);
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
            max = Math.max(dp[i],max);
        }
        return max;
    }
    static boolean compare(String str1,String str2){
        if(Math.abs(str1.length()-str2.length())!=1) return false;
        if(str1.length()>str2.length()){
            int i=0;
            int j=0;
            while(i<str1.length() && j<str2.length()){
                if(str1.charAt(i)==str2.charAt(j) ){
                    i++;
                    j++;
                }
                else{
                    i++;
                }
            }

            if((i==str1.length() && j==str2.length()) || (i+1 == str1.length() && j== str2.length())){ 
                return true;
            }
            return false;
        }else{
            return false;
        }
    }
    // Kinda Brute Force
    public int longestStrChain_brute(String[] words) {
        // HashSet<String> hash = new HashSet<>();
        Arrays.sort(words, Comparator.comparing(s -> s.length()));
        HashMap<String, Integer> dp = new HashMap<>();
        int ans = 0;
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                String predecessor = word.substring(0, i) + word.substring(i + 1);
                int val = Math.max(dp.getOrDefault(word, 0), dp.getOrDefault(predecessor, 0) + 1);
                dp.put(word, val);
            }
            ans = Math.max(ans, dp.get(word));
        }
        return ans;
    }

    
    ///********************************************************************************************************* */
    //!24/9/23 
    public double champagneTower(int poured, int ro, int query_glass) {
        double[][] dp = new double[101][101];
        dp[0][0] = poured;
        for (int i = 0; i < ro; i++) {
            for (int j = 0; j < i + 1; j++) {
                double rem = Math.max(dp[i][j] - 1.0, 0);
                dp[i + 1][j] += rem / 2.0;
                dp[i + 1][j + 1] += rem / 2.0;
            }
        }
        // System.out.println(Arrays.deepToString(dp));
        return Math.min(dp[ro][query_glass], 1.0);
    }

    
    ///********************************************************************************************************* */
    //!25/9/23 
    public char findTheDifference(String s, String t) {
        int count[]=new int[26];
        for(int i=0;i<t.length();i++)
        {
            count[t.charAt(i)-'a']++;
        }
        for(int i=0;i<s.length();i++)
        {
            count[s.charAt(i)-'a']--;
        }
        
        for(int i=0;i<26;i++){
            if(count[i]>0) return (char)('a'+i);
        }
        return 'a';
    }
    ///********************************************************************************************************* */
    //!26/9/23 
    // https://www.youtube.com/watch?v=ht-zr2xlfdk
    public String removeDuplicateLetters(String s) {
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++)
            lastIndex[s.charAt(i) - 'a'] = i;
        boolean[] seen = new boolean[26];
        Stack<Integer> st = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';

            if (seen[c])
                continue;

            while (!st.isEmpty() && st.peek() > c && i < lastIndex[st.peek()]) {
                seen[st.pop()] = false;
            }
            st.push(c);
            seen[c] = true;
        }
        StringBuilder sb = new StringBuilder();

        while (!st.isEmpty()) {
            sb.append((char) (st.pop() + 'a'));
        }
        return sb.reverse().toString();
    }
    
    ///********************************************************************************************************* */
    //!27/9/23
    public String decodeAtIndex(String inputString, int k) {
        long decodedLength = 0;

        for (char character : inputString.toCharArray()) {
            if (Character.isDigit(character)) {
                decodedLength *= (character - '0');
            } else {
                decodedLength++;
            }
        }

        for (int i = inputString.length() - 1; i >= 0; i--) {
            char currentChar = inputString.charAt(i);

            if (Character.isDigit(currentChar)) {
                decodedLength /= (currentChar - '0');
                k %= decodedLength;
            } else {
                if (k == 0 || decodedLength == k) {
                    return String.valueOf(currentChar);
                }
                decodedLength--;
            }
        }

        return "";
    }
    ///********************************************************************************************************* */
    //!28/9/23
    public int[] sortArrayByParity(int[] nums) {
        int front = 0;
        int end = nums.length - 1;
        while (front < end) {
            if (nums[front] % 2 != 0) {
                int temp = nums[front];
                nums[front] = nums[end];
                nums[end] = temp;
                end--;
                continue;
            }
            front++;
        }
        return nums;
    }

    ///********************************************************************************************************* */
    //!29/9/23 
    public boolean isMonotonic(int[] nums) {
        int min = 0;
        int max = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] == nums[i]) {
                max++;
                min++;
                continue;
            }
            if (nums[i - 1] > nums[i]) {
                max++;
            } else {
                min++;
            }
        }
        // System.out.println(max+" "+min);
        return ((max + 1 == nums.length) || (min + 1 == nums.length)) ? true : false;
    }
    
    // good approach
    public boolean isMonotonic_good(int[] nums) {
        int n = nums.length;
        
        if (n <= 1) {
            return true; // A single element array is considered monotonic.
        }
        
        int direction = 0; // 0: undetermined, 1: increasing, -1: decreasing
        
        for (int i = 1; i < n; i++) {
            int diff = nums[i] - nums[i - 1];
            
            if (diff > 0) {
                if (direction == -1) {
                    return false; // Not monotonic if it was decreasing before.
                }
                direction = 1;
            } else if (diff < 0) {
                if (direction == 1) {
                    return false; // Not monotonic if it was increasing before.
                }
                direction = -1;
            }
            // If diff == 0, continue to the next element.
        }
        
        return true;
    }

}