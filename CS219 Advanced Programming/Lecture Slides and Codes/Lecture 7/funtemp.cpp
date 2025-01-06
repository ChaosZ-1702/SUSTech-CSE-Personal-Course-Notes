// funtemp.cpp -- using a function template
#include <iostream>
// function template prototype

template <typename T> // or class T
void Swap(T &a, T &b);

int main()
{
    using namespace std;
    int i = 10;
    int j = 20;
    cout << "i, j = " << i << ", " << j << ".\n";
    cout << "Using compiler-generated int swapper:\n";
    Swap(i,j); // generates void Swap(int &, int &)
    cout << "Now i, j = " << i << ", " << j << ".\n";
    void (* fun_p)(int &,int &) = Swap;
    cout<< "function address: " <<  (void *)*fun_p <<endl;
    //cout<< (void *) Swap <<endl;
    
    double x = 24.5;
    double y = 81.7;
    cout << "x, y = " << x << ", " << y << ".\n";
    cout << "Using compiler-generated double swapper:\n";
    Swap(x,y); // generates void Swap(double &, double &)
    cout << "Now x, y = " << x << ", " << y << ".\n";
    
    void (* fun_p2)(double &, double &) = Swap;
	cout<< "function address: " <<  (void *)*fun_p2 <<endl;
    // cin.get();
    //cout<< (void *) Swap <<endl;
    
    return 0;
}

// function template definition
template <typename T> // or class T
void Swap(T &a, T &b)
{
    T temp; // temp a variable of type T
    temp = a;
    a = b;
    b = temp;
}
