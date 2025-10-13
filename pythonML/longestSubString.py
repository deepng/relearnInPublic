# Given a string s, find the length of the longest substring without duplicate characters.
def lengthOfLongestSubstring(s: str) -> int:
    return brute_force(s)

def slidingWindow(s: str) -> int:
    windowSize = 0
    maxWindowSize = 0
    j = 0
    for i in range(j, len(s)):
        if(s[i] not in s[j:i]):
            windowSize += 1
        elif(windowSize > maxWindowSize):
            maxWindowSize = windowSize
            j += 1
            windowSize = 0
    return maxWindowSize

def brute_force(s: str) -> int:
    e = []
    max = 0
    for i in range(0, len(s)):
        currMax = 0
        a = []
        a.append(s[i])
        currMax += 1
        for j in range(i + 1, len(s)):
            if (s[i] == s[j] or s[j] in a):
                e.append(a)
                break
            a.append(s[j])
            currMax += 1
        if currMax > max:
            max = currMax
    return max


if __name__ == '__main__':
    x = lengthOfLongestSubstring(' ')
    assert 1 == x