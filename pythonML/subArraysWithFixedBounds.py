def countSubarrays(nums, minK, maxK):
    print(f"nums = {nums}, mink = {minK}, maxk = {maxK} ")
    counter = 0
    if not minK in nums or not maxK in nums:
        return counter
    for l in range(len(nums)):
        for r in range(l+1, len(nums)+1):
            print(f'l = {l} and r = {r}')
            tmp = nums[l:r]
            tmp.sort() # can we do it without sort??
            if not minK in nums or not maxK in nums:
                break
            print(tmp)
            if(tmp[-1] == maxK and tmp[0] == minK):
                counter += 1
                print(f'counter to {counter}')
    return counter


if __name__ == '__main__':
    nums = [([1, 2, 3, 2, 1], 1, 3, 5), ([1, 1, 1], 1, 1, 6)]
    for num, mink, maxk, expected in nums:
        count = countSubarrays(num, mink, maxk)
        assert count == expected, f"wrong answer {count}, expected {expected}"