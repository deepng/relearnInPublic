#Datatypes - Strings -> "", int -> 0, tuple -> (), list -> [], dict/map -> {}

name = "deep"
message = f' my name is {name} '
up_message = message.strip().upper()

print(message.strip().title())

fruits = ["apple", "banana", "mango"]
fruits.append('cherry')
fruits.sort()
print(fruits)
fruit_len =len(fruits)
print(f"The last fruits is {fruits[-1]}")
print(f"len of list {fruit_len}")
fruits.pop()
print(f"len of list {len(fruits)}")
print(f"reversing the list {fruits[::-1]}")
print(f"Print every alternate fruit {fruits[::2]}")


nums = [[1, 2, 3, 2, 1], 1, 3, 5, [1, 1, 1], 1, 1, 6]
print(nums)
print(f"every 1st index = {nums[::4]}")
print(f"every 2nd index = {nums[1::4]}")
print(f"every 3rd index = {nums[2::4]}")
print(f"every 4th index = {nums[3::4]}")