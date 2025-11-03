#https://leetcode.com/problems/longest-harmonious-subsequence/description/?envType=problem-list-v2&envId=sliding-window
import math

def findLHS(nums: list[int]) -> int:
        counter = sorted(nums)
        result = 0
        left = 0
        right = 1
        while(right < len(nums) and left < len(nums)):
            difference = counter[right] - counter[left]
            if difference == 1:
                result = max(result, right - left + 1)
            if  difference <= 1:
                 right += 1
            elif difference > 1:
                 left += 1
        return result


def findLHSMap(nums: list[int]) -> int:
    kv = {}
    result = 0
    for n in nums:
         kv[n] = kv.setdefault(n, 0) + 1
    for k in kv.keys():
         if kv.get(k+1):
              result = max(result, kv[k] + kv[k+1])
    return result

if __name__ == '__main__':
     nums = [1,3,2,2,5,2,3,7]   
     print(findLHS(nums))
     print(findLHSMap(nums))