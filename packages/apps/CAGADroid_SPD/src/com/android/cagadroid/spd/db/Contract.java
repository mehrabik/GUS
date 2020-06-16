package com.android.cagadroid.spd.db;

import com.android.cagadroid.spd.db.basetables.CompoundTable;
import com.android.cagadroid.spd.db.basetables.PolicyTable;
import com.android.cagadroid.spd.db.basetables.PrimitiveTable;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public final class Contract {

    public static final String[] PRIMITIVES = {"Action", "Operation", "Domain", "Type",
            "Role", "Modality", "Subject", "Object", "User", "Authenticator"};

    public static final String[] COMPOUNDS = {"Subject_Domain", "Object_Type", "User_Role", "Authenticator_Modality"};

    /**
     * Contains the possible types of action.
     */
    public static class Action extends PrimitiveTable {
        public static final Action INSTANCE = new Action();

        private Action() {}

        @Override
        public String tableName() { return "actions"; }
    }

    /**
     * Contains possible operations.
     */
    public static class Operation extends PrimitiveTable {
        public static final Operation INSTANCE = new Operation();

        private Operation() {}

        @Override
        public String tableName() { return "operations"; }
    }

    /**
     * Contains domain definitions (for subjects)
     */
    public static class Domain extends PrimitiveTable {
        public static final Domain INSTANCE = new Domain();

        private Domain() { }

        @Override
        public String tableName() { return "domains"; }
    }

    /**
     * Contains type definitions (for objects)
     */
    public static class Type extends PrimitiveTable {
        public static final Type INSTANCE = new Type();

        private Type() { }

        @Override
        public String tableName() { return "types"; }
    }

    /**
     * Contains role definitions (for users)
     */
    public static class Role extends PrimitiveTable {
        public static final Role INSTANCE = new Role();

        private Role() { }

        @Override
        public String tableName() { return "roles"; }
    }

    /**
     * Contains modality definitions (for authenticators)
     */
    public static class Modality extends PrimitiveTable {
        public static final Modality INSTANCE = new Modality();

        private Modality() { }

        @Override
        public String tableName() { return "modalities"; }
    }

    /**
     * Contains subjects (apps)
     */
    public static class Subject extends PrimitiveTable {
        public static final Subject INSTANCE = new Subject();

        private Subject() { }

        @Override
        public String tableName() { return "subjects"; }
    }

    /**
     * Contains objects (resources)
     */
    public static class Object extends PrimitiveTable {
        public static final Object INSTANCE = new Object();

        private Object() { }

        @Override
        public String tableName() { return "objects"; }
    }

    /**
     * Contains user definitions (physical users)
     */
    public static class User extends PrimitiveTable {
        public static final User INSTANCE = new User();

        private User() { }

        @Override
        public String tableName() { return "users"; }
    }

    /**
     * Contains all possible authenticators (e.g., fingerprint, PIN, different IA methods)
     */
    public static class Authenticator extends PrimitiveTable {
        public static final Authenticator INSTANCE = new Authenticator();

        private Authenticator() { }

        @Override
        public String tableName() { return "authenticators"; }
    }

    /**
     * Contains object to type assignments
     */
    public static class Object_Type extends CompoundTable {
        public static final Object_Type INSTANCE = new Object_Type();

        private Object_Type() {}

        @Override
        public String individualIdName() { return "ObjectID"; }

        @Override
        public String groupIdName() { return "TypeID"; }

        @Override
        public String tableName() { return "object_type"; }
    }

    /**
     * Contains subject to domain assignments
     */
    public static class Subject_Domain extends CompoundTable {
        public static final Subject_Domain INSTANCE = new Subject_Domain();

        private Subject_Domain() {}

        @Override
        public String individualIdName() { return "SubjectID"; }

        @Override
        public String groupIdName() { return "DomainID"; }

        @Override
        public String tableName() { return "subject_domain"; }
    }

    /**
     * Contains user to role assignments
     */
    public static class User_Role extends CompoundTable {
        public static final User_Role INSTANCE = new User_Role();

        private User_Role() {}

        @Override
        public String individualIdName() { return "UserID"; }

        @Override
        public String groupIdName() { return "RoleID"; }

        @Override
        public String tableName() { return "user_role"; }
    }

    /**
     * Contains authenticator to modality assignments
     */
    public static class Authenticator_Modality extends CompoundTable {
        public static final Authenticator_Modality INSTANCE = new Authenticator_Modality();

        private Authenticator_Modality() {}

        @Override
        public String individualIdName() { return "AuthenticatorID"; }

        @Override
        public String groupIdName() { return "ModalityID"; }

        @Override
        public String tableName() { return "authenticator_modality"; }
    }

    /**
     * Contains all policies
     */
    public static class Policy extends PolicyTable {
        public static final Policy INSTANCE = new Policy();

        private Policy() {}
    }
}
