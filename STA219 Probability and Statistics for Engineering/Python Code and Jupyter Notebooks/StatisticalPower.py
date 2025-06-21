# ======================= Example 6.4 ======================== #

import numpy as np
from scipy.stats import norm


def Testing(n, p_true, p0, alpha):
    # p_true is the true value
    sample = np.random.binomial(1, p_true, n)
    p_hat = np.mean(sample)
    test_stat = (p_hat - p0) / np.sqrt(p0 * (1 - p0) / n)
    # the alternative hypothesis is one-sided, right-tailed
    z_alpha = norm.ppf(1 - alpha) # ppf is the inverse of CDF
    if test_stat > z_alpha:
        return 1 # reject
    else:
        return 0 # fail to reject


# First consider the case when p_true = 0.025, p0 = 0.02
np.random.seed(20241220)
rep = 10000
cnt = 0

n = 1000 # if sample size is only 1000

for i in range(rep):
    cnt = cnt + Testing(n, 0.025, 0.02, 0.05)
    power = cnt / rep

print("When the sample size is %s:" % n)
print("\t number of times fail to reject: %s" % (rep - cnt))
print("\t power of the test: %.4f" % (cnt / rep))


np.random.seed(20241220)
cnt = 0
n = 7000 # if sample size is 7000

for i in range(rep):
    cnt = cnt + Testing(n, 0.025, 0.02, 0.05)
    power = cnt / rep

print("When the sample size is %s:" % n)
print("\t number of times fail to reject: %s" % (rep - cnt))
print("\t power of the test: %.4f" % (cnt / rep))
# 7000 is still not enough


np.random.seed(20241220)
cnt = 0
n = 7412 # if sample size is 7412

for i in range(rep):
    cnt = cnt + Testing(n, 0.025, 0.02, 0.05)
    power = cnt / rep

print("When the sample size is %s:" % n)
print("\t number of times fail to reject: %s" % (rep - cnt))
print("\t power of the test: %.4f" % (cnt / rep))


# Then consider the case when p_true = 0.05, p0 = 0.02
np.random.seed(20241220)
rep = 10000
cnt = 0

n = 200 # if sample size is only 200

for i in range(rep):
    cnt = cnt + Testing(n, 0.05, 0.02, 0.05)
    power = cnt / rep

print("When the sample size is %s:" % n)
print("\t number of times fail to reject: %s" % (rep - cnt))
print("\t power of the test: %.4f" % (cnt / rep))
# 200 is not enough


np.random.seed(20241220)
rep = 10000
cnt = 0

n = 288 # if sample size is 288

for i in range(rep):
    cnt = cnt + Testing(n, 0.05, 0.02, 0.05)
    power = cnt / rep

print("When the sample size is %s:" % n)
print("\t number of times fail to reject: %s" % (rep - cnt))
print("\t power of the test: %.4f" % (cnt / rep))




# ======================= Example 6.5 ======================== #
def Testing2(n, mu_true, mu0, alpha):
    # p_true is the true value
    sample = np.random.normal(mu_true, 15, n) # standard deviation is 15
    mu_hat = np.mean(sample)
    test_stat = (mu_hat - mu0) / np.sqrt(15 ** 2 / n)
    # the alternative hypothesis is one-sided, right-tailed
    z_alpha = norm.ppf(1 - alpha) # ppf is the inverse of CDF
    if test_stat > z_alpha:
        return 1 # reject
    else:
        return 0 # fail to reject


# Consider the case when mu_true = 105, mu0 = 100
np.random.seed(20241220)
rep = 10000
cnt = 0

n = 78 # if sample size is 78

for i in range(rep):
    cnt = cnt + Testing2(n, 105, 100, 0.05)
    power = cnt / rep

print("When the sample size is %s:" % n)
print("\t number of times fail to reject: %s" % (rep - cnt))
print("\t power of the test: %.4f" % (cnt / rep))


np.random.seed(20241220)
rep = 10000
cnt = 0

n = 70 # if sample size is only 70

for i in range(rep):
    cnt = cnt + Testing2(n, 105, 100, 0.05)
    power = cnt / rep

print("When the sample size is %s:" % n)
print("\t number of times fail to reject: %s" % (rep - cnt))
print("\t power of the test: %.4f" % (cnt / rep))

