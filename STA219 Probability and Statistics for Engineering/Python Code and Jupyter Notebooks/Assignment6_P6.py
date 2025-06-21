import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation

# 森林尺寸
rows, cols = 20, 50
num_trees = rows * cols

# 点燃概率
left_prob = 0.8
up_prob = 0.3

# 蒙特卡罗次数（满足0.005误差，95%置信度）
n = 10000

# 记录烧毁比例 & 烧毁树数
burn_ratios = []
burn_counts = []

for sim in range(n):
    forest = np.zeros((rows, cols), dtype=int)
    forest[0, 0] = 1

    for r in range(rows):
        for c in range(cols):
            if forest[r, c] == 0:
                left_burn = forest[r, c-1] == 1 if c > 0 else False
                up_burn = forest[r-1, c] == 1 if r > 0 else False

                prob = 0
                if left_burn:
                    prob = 1 - (1 - prob) * (1 - left_prob)
                if up_burn:
                    prob = 1 - (1 - prob) * (1 - up_prob)

                if prob > 0 and np.random.rand() < prob:
                    forest[r, c] = 1

    burned = np.sum(forest)
    burn_counts.append(burned)
    burn_ratios.append(burned / num_trees)

# 问题 (1)：估计超过30%烧毁的概率
burn_ratios = np.array(burn_ratios)
p_estimate = np.mean(burn_ratios > 0.3)
print(f"(1) The probability of X > 30%: {p_estimate:.4f}")

# 问题 (2)：预测受影响树木的均值
burn_counts = np.array(burn_counts)
mean_X = np.mean(burn_counts)
print(f"(2) Mean of X: {mean_X:.2f}")

# 问题 (3)：标准差
std_X = np.std(burn_counts, ddof=1)
print(f"(3) Standard deviation of X: {std_X:.2f}")

# 附加：绘制烧毁比例分布
plt.hist(burn_ratios, bins=30, edgecolor='black')
plt.xlabel('Burnt ratio')
plt.ylabel('Times')
plt.title('Monte Carlo Simulation: Burnt Ratio Distribution')
plt.show()