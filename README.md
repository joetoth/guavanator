Guavanator
==========

Eclipse plugin to generate Guava versions of equals, hashcode, toString, getters/setters and a Builder.

Install
==========

Help -> Install New Software
https://raw.github.com/joetoth/guavanator/master/site/site.xml


Usage
==========
Right-Click -> Source -> Generate Guava Bean

	public class Person implements Serializable {
    	private String firstName;
    	private final String lastName;
    	private int age;
    	private List<String> siblingNames = Lists.newArrayList();
    	private final ImmutableSet<String> parentNames;
  	}

Will Generate
==========
	
	public class Person implements Serializable {
		private String firstName;
		private final String lastName;
		private int age;
		private List<String> siblingNames = Lists.newArrayList();
		private final ImmutableSet<String> parentNames;
	
		// Everything below is auto-generated by the Guavanator
	
		public String getFirstName() {
			return firstName;
		}
	
		public Person setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
	
		public String getLastName() {
			return lastName;
		}
	
		public Person setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
	
		public int getAge() {
			return age;
		}
	
		public Person setAge(int age) {
			this.age = age;
			return this;
		}
	
		public List<String> getSiblingNames() {
			return siblingNames;
		}
	
		public Person setSiblingNames(List<String> siblingNames) {
			this.siblingNames = siblingNames;
			return this;
		}
	
		public ImmutableSet<String> getParentNames() {
			return parentNames;
		}
	
		public Person setParentNames(ImmutableSet<String> parentNames) {
			this.parentNames = parentNames;
			return this;
		}
	
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Person)) {
				return false;
			}
	
			Person that = (Person) obj;
	
			return Objects.equals(this.firstName, that.firstName)
					.equals(this.lastName, that.lastName)
					.equals(this.age, that.age)
					.equals(this.siblingNames, that.siblingNames)
					.equals(this.parentNames, that.parentNames);
		}
	
		@Override
		public int hashCode() {
			return Objects.hashCode(
					firstName,
					lastName,
					age,
					siblingNames,
					parentNames);
		}
	
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("firstName", firstName)
					.add("lastName", lastName)
					.add("age", age)
					.add("siblingNames", siblingNames)
					.add("parentNames", parentNames)
					.toString();
		}
	
		public static class Builder {
			private String firstName;
			private String lastName;
			private int age;
			private List<String> siblingNames;
			private ImmutableSet<String> parentNames;
	
			public Builder setFirstName(String firstName) {
				this.firstName = firstName;
				return this;
			}
	
			public Builder setLastName(String lastName) {
				this.lastName = lastName;
				return this;
			}
	
			public Builder setAge(int age) {
				this.age = age;
				return this;
			}
	
			public Builder setSiblingNames(List<String> siblingNames) {
				this.siblingNames = siblingNames;
				return this;
			}
	
			public Builder setParentNames(ImmutableSet<String> parentNames) {
				this.parentNames = parentNames;
				return this;
			}
	
			public Person build() {
				return new Person(this);
			}
		}
	
		private Person(Builder builder) {
			this.firstName = builder.firstName;
			this.lastName = builder.lastName;
			this.age = builder.age;
			this.siblingNames = builder.siblingNames;
			this.parentNames = builder.parentNames;
		}
	
		public static Builder builder() {
			return new Builder();
		}
	}
