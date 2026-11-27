package leetcode;

import java.util.ArrayList;
import java.util.List;

public class leetcode_46_dfs {
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        dfs(nums, new boolean[nums.length], new ArrayList<>(), res);
        return res;
    }
    
    static void dfs(int[] nums, boolean[] visited, List<Integer> path, List<List<Integer>> res) {
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                continue;
            }
            visited[i] = true;
            path.add(nums[i]);
            dfs(nums, visited, path, res);
            path.removeLast();
            visited[i] = false;
        }
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(permute(nums));
    }
}