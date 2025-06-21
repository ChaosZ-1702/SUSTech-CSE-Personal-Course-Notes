#============================== Example 4.7 ==============================#
# This block of code may take 1-2 minutes to run
import numpy as np
np.random.seed(20241120)

# Write a function to determine if a random draw contains at least k consecutive numbers
# This may not be the most efficient realization
def consecutiveCount(arr, k):
    if k <= 1:
        return True  # Any array has at least one consecutive number if k=1
    
    # Sort the array
    sorted_arr = np.sort(arr)
    
    # Initialize count of consecutive numbers
    consecutive_count = 1
    
    # Iterate through the sorted array and check for consecutive numbers
    for i in range(1, len(sorted_arr)):
        if sorted_arr[i] == sorted_arr[i - 1] + 1:
            consecutive_count += 1
            # Check if the count of consecutive numbers has reached k
            if consecutive_count >= k:
                return 1
        else:
            # Reset the consecutive count if the sequence breaks
            consecutive_count = 1
    
    return 0


# Repeat the random sampling n times and record the number of times with more than k consecutive numbers
n = 100000

N1 = 5141; m1 = 124; k1 = 6
cnt1 = 0
for i in range(n):
    result = np.random.choice(np.arange(1, N1 + 1), size = m1, replace = False)
    cnt1 = cnt1 + consecutiveCount(result, k1)

N2 = 1138; m2 = 514; k2 = 14
cnt2 = 0
for i in range(n):
    result = np.random.choice(np.arange(1, N2 + 1), size = m2, replace = False)
    cnt2 = cnt2 + consecutiveCount(result, k2)

# Compute the estimated probability
print("Prob. of the case in Wuhan: %.6f" % (cnt1 / n))
print("Prob. of the case in Laohekou: %.6f" % (cnt2 / n))



#============================== Example 4.8 ==============================#
n = 16577
cnt = 0
finish_times = np.zeros(n)
np.random.seed(20241120)

for i in range(n):
    active_users = np.random.binomial(250, 0.3)
    tasks = np.random.geometric(0.15, size = active_users)
    total_tasks = np.sum(tasks)
    # Pay attention to the parameterization of the Gamma distribution in Python, scale = 1/lambda
    # https://numpy.org/doc/2.0/reference/random/generated/numpy.random.gamma.html
    computer_time = np.random.gamma(shape = 10, scale = 1 / 3, size = total_tasks) 
    finish_times[i] = np.sum(computer_time)
    
    if finish_times[i] <= 1440:
        cnt = cnt + 1

print("Prob. of finishing all tasks: %.6f" % (cnt / n))
# The probability is 0.174010 running with a very large n



#==================== Another way to implement Example 4.8 ====================#
n = 16577
cnt = 0
finish_times2 = np.zeros(n)
np.random.seed(20241120)

for i in range(n):
    active_users = np.random.binomial(250, 0.3)
    # Pay attention to the output of np.random.negative_binomial
    # https://numpy.org/doc/2.1/reference/random/generated/numpy.random.negative_binomial.html
    total_tasks = np.random.negative_binomial(active_users, 0.15) + active_users
    finish_times2[i] = np.random.gamma(10 * total_tasks, 1 / 3)
    
    if finish_times2[i] <= 1440:
        cnt = cnt + 1

print("Prob. of finishing all tasks with the second method: %.6f" % (cnt / n))



#============================== Example 4.8 (Continued) ==============================#
print("Expectation of the total requested computer time: %.4f min" % np.mean(finish_times))
print("Standard deviation of the total requested computer time: %.4f min" % np.std(finish_times))
print("Results of the second method:")
print("Expectation of the total requested computer time: %.4f min" % np.mean(finish_times2))
print("Standard deviation of the total requested computer time: %.4f min" % np.std(finish_times2))

