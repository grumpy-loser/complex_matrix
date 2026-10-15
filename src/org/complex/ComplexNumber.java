package org.complex;

import java.util.Objects;

/// Represents a complex number.
///
/// Initialized by either single `double` value, representing
/// only real part, or by two `double` values - real and imaginary
/// parts of complex number.
///
/// Complex numbers support arithmetical operations:
/// [add][add(ComplexNumber)], [subtract][subtract(ComplexNumber)],
/// [multiply][multiply(ComplexNumber)], [divide][divide(ComplexNumber)].
public class ComplexNumber
{
    /// Real part of complex number.
    private final double real;
    /// Imagine part of complex number.
    private final double imag;

    /// Constructs complex number.
    ///
    /// @param real real part of number
    /// @param imag imaginary part of number
    public ComplexNumber(double real, double imag)
    {
        this.real = real;
        this.imag = imag;
    }

    /// Constructs complex number.
    /// Imaginary part is set to zero.
    ///
    /// @param real real part of number
    public ComplexNumber(double real)
    {
        this.real = real;
        this.imag = 0;
    }

    /// Constructs complex number as a copy of another one.
    ///
    /// @param number another complex number
    public ComplexNumber(ComplexNumber number)
    {
        this.real = number.real;
        this.imag = number.imag;
    }

    /// Constructs zero complex number.
    /// Both real and imaginary parts are set to zero.
    public ComplexNumber()
    {
        this.real = 0;
        this.imag = 0;
    }

    /// Returns real part of the complex number.
    public double real()
    {
        return this.real;
    }

    /// Returns imaginary part of the complex number.
    public double imaginary()
    {
        return this.imag;
    }

    /// Returns `true` if this complex number equals zero.
    public boolean isZero()
    {
        return this.equals(new ComplexNumber());
    }

    /// Returns modulus of this complex number.
    public double modulus()
    {
        return Math.sqrt(this.real * this.real + this.imag * this.imag);
    }

    /// Returns a complex number which is conjugate to this one.
    public ComplexNumber conjugate()
    {
        return new ComplexNumber(this.real, -this.imag);
    }

    /// Adds the given complex number to this one, and returns
    /// their sum.
    ///
    /// @param other complex number being added to this one
    /// @return sum of this and `other` numbers
    public ComplexNumber add(ComplexNumber other)
    {
        Objects.requireNonNull(other, "Value cannot be null");
        return new ComplexNumber(this.real + other.real, this.imag + other.imag);
    }

    /// Adds the given `double` number to this one, and returns
    /// their sum.
    ///
    /// @param other `double` number being added to this one
    /// @return sum of this and `other` numbers
    public ComplexNumber add(double other)
    {
        return new ComplexNumber(this.real + other, this.imag);
    }

    /// Subtracts the given complex number from this one, and returns
    /// their difference.
    ///
    /// @param other complex number being subtracted from this one
    /// @return difference of this and `other` numbers
    public ComplexNumber subtract(ComplexNumber other)
    {
        Objects.requireNonNull(other, "Value cannot be null");
        return new ComplexNumber(this.real - other.real, this.imag - other.imag);
    }

    /// Subtracts the given `double` number from this one, and returns
    /// their difference.
    ///
    /// @param other `double` number being subtracted from this one
    /// @return difference of this and `other` numbers
    public ComplexNumber subtract(double other)
    {
        return new ComplexNumber(this.real - other, this.imag);
    }

    /// Multiplies the given complex number to this one, and returns
    /// their product.
    ///
    /// @param other complex number being multiplied to this one
    /// @return product of this and `other` numbers
    public ComplexNumber multiply(ComplexNumber other)
    {
        Objects.requireNonNull(other, "Value cannot be null");
        return new ComplexNumber(this.real * other.real - this.imag * other.imag,
                this.real * other.imag + this.imag * other.real );
    }

    /// Multiplies the given `double` number to this one, and returns
    /// their product.
    ///
    /// @param other `double` number being multiplied to this one
    /// @return product of this and `other` numbers
    public ComplexNumber multiply(double other)
    {
        return new ComplexNumber(this.real * other,this.imag * other);
    }

    /// Divides this number by the given complex number, and returns
    /// their quotient.
    ///
    /// @param other complex number that divides this one
    /// @return quotient of this and `other` numbers
    public ComplexNumber divide(ComplexNumber other)
    {
        Objects.requireNonNull(other, "Value cannot be null");
        double divider = other.real * other.real + other.imag * other.imag;
        if (divider == 0)
        {
            throw new ArithmeticException("Divide by zero");
        }
        return new ComplexNumber((this.real * other.real + this.imag * other.imag) / divider,
                (this.real * other.imag - this.imag * other.real ) / divider);
    }

    /// Divides this number by the given `double` number, and returns
    /// their quotient.
    ///
    /// @param other `double` number that divides this one
    /// @return quotient of this and `other` numbers
    public ComplexNumber divide(double other)
    {
        if (other == 0)
        {
            throw new ArithmeticException("Divide by zero");
        }
        return new ComplexNumber(this.real / other,this.imag / other);
    }

    /// Checks whether this complex number is equal
    /// to the given one.
    ///
    /// If `other` is null, or neither complex nor `double`
    /// number, returns `false`.
    ///
    /// @param other another number to compare with
    /// @return `true` if both numbers are equal; otherwise `false`
    @Override
    public boolean equals(Object other)
    {
        if (other instanceof ComplexNumber complexNumber)
        {
            return (this.real == complexNumber.real && this.imag == complexNumber.imag);
        }
        if (other instanceof Double value)
        {
            return (this.real == value && this.imag == 0);
        }
        return false;
    }

    /// Computes hash code for this complex number. This method
    /// is required to be implemented together with [equals].
    ///
    /// @return hash code for this complex number
    @Override
    public int hashCode()
    {
        return java.util.Objects.hash(real, imag);
    }

    /// Returns stringified representation of this complex number.
    @Override
    public String toString()
    {
        if (this.imag == 0)
        {
            return Double.toString(this.real);
        }
        if (this.real == 0)
        {
            return this.imag + "i";
        }
        if (this.imag > 0)
        {
            return this.real + "+" + this.imag + "i";
        }
        else
        {
            return this.real + "" + this.imag + "i";
        }
    }
}
