#Given an array of integers, find the maximum sum of a subarray with a fixed window size.
def fixedWindowSize(nums: list[int], size: int) -> int:
    max = 0
    l = 0
    r = size
    while r < len(nums):
        res = sum(nums[l:r])
        if(max < res):
            max = res
        l += 1
        r += 1
    return max

if __name__ == '__main__':
    nums = [2, 1, 5, 1, 3, 2]
    print(fixedWindowSize(nums, 3))

