#Given an array of positive integers and an integer K, find the length of the longest subarray whose sum is less than K.
def longestSubarrayOfK(nums: list[int], k: int) -> int:
    subArray = {}
    l = 0
    r = 0
    while r < len(nums):
        curr = sum(nums[l:r])
        if(curr < k):
            if (len(subArray) <= r-l):
                subArray = nums[l:r]
            r += 1
        elif curr >= k:
            if(l == r):
                r += 1
            l += 1
            
    return len(subArray)


if __name__ == '__main__':
    nums = [1, 2, 3, 4, 5]
    print(longestSubarrayOfK(nums, 5))